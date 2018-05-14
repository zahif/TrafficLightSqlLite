/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.googleapis.xml.atom;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Types;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

/**
 * {@link Beta} <br/>
 * Utilities for working with the Atom XML of Google Data APIs.
 *
 * @since 1.0
 * @author Yaniv Inbar
 */
@Beta
public class GoogleAtom {

  /**
   * GData namespace.
   *
   * @since 1.0
   */
  public static final String GD_NAMESPACE = "http://schemas.google.com/g/2005";

  /**
   * Content type used on an error formatted in XML.
   *
   * @since 1.5
   */
  public static final String ERROR_CONTENT_TYPE = "application/vnd.google.gdata.error+xml";

  // TODO(yanivi): require XmlNamespaceDictory and include xmlns declarations since there is no
  // guarantee that there is a match between Google's mapping and the one used by client

  /**
   * Returns the fields mask to use for the given data class of key/value pairs. It cannot be a
   * {@link Map}, {@link GenericData} or a {@link Collection}.
   *
   * @param dataClass data class of key/value pairs
   */
  public static String getFieldsFor(Class<?> dataClass) {
    StringBuilder fieldsBuf = new StringBuilder();
    appendFieldsFor(fieldsBuf, dataClass, new int[1]);
    return fieldsBuf.toString();
  }

  /**
   * Returns the fields mask to use for the given data class of key/value pairs for the feed class
   * and for the entry class. This should only be used if the feed class does not contain the entry
   * class as a field. The data classes cannot be a {@link Map}, {@link GenericData} or a
   * {@link Collection}.
   *
   * @param feedClass feed data class
   * @param entryClass entry data class
   */
  public static String getFeedFields(Class<?> feedClass, Class<?> entryClass) {
    StringBuilder fieldsBuf = new StringBuilder();
    appendFeedFields(fieldsBuf, feedClass, entryClass);
    return fieldsBuf.toString();
  }

  private static void appendFieldsFor(
      StringBuilder fieldsBuf, Class<?> dataClass, int[] numFields) {
    if (Map.class.isAssignableFrom(dataClass) || Collection.class.isAssignableFrom(dataClass)) {
      throw new IllegalArgumentException(
          "cannot specify field mask for a Map or Collection class: " + dataClass);
    }
    ClassInfo classInfo = ClassInfo.of(dataClass);
    for (String name : new TreeSet<String>(classInfo.getNames())) {
      FieldInfo fieldInfo = classInfo.getFieldInfo(name);
      if (fieldInfo.isFinal()) {
        continue;
      }
      if (++numFields[0] != 1) {
        fieldsBuf.append(',');
      }
      fieldsBuf.append(name);
      // TODO(yanivi): handle Java arrays?
      Class<?> fieldClass = fieldInfo.getType();
      if (Collection.class.isAssignableFrom(fieldClass)) {
        // TODO(yanivi): handle Java collection of Java collection or Java map?
        fieldClass = (Class<?>) Types.getIterableParameter(fieldInfo.getField().getGenericType());
      }
      // TODO(yanivi): implement support for map when server implements support for *:*
      if (fieldClass != null) {
        if (fieldInfo.isPrimitive()) {
          if (name.charAt(0) != '@' && !name.equals("text()")) {
            // TODO(yanivi): wait for bug fix from server to support text() -- already fixed???
            // buf.append("/text()");
          }
        } else if (!Collection.class.isAssignableFrom(fieldClass)
            && !Map.class.isAssignableFrom(fieldClass)) {
          int[] subNumFields = new int[1];
          int openParenIndex = fieldsBuf.length();
          fieldsBuf.append('(');
          // TODO(yanivi): abort if found cycle to avoid infinite loop
          appendFieldsFor(fieldsBuf, fieldClass, subNumFields);
          updateFieldsBasedOnNumFields(fieldsBuf, openParenIndex, subNumFields[0]);
        }
      }
    }
  }

  private static void appendFeedFields(
      StringBuilder fieldsBuf, Class<?> feedClass, Class<?> entryClass) {
    int[] numFields = new int[1];
    appendFieldsFor(fieldsBuf, feedClass, numFields);
    if (numFields[0] != 0) {
      fieldsBuf.append(",");
    }
    fieldsBuf.append("entry(");
    int openParenIndex = fieldsBuf.length() - 1;
    numFields[0] = 0;
    appendFieldsFor(fieldsBuf, entryClass, numFields);
    updateFieldsBasedOnNumFields(fieldsBuf, openParenIndex, numFields[0]);
  }

  private static void updateFieldsBasedOnNumFields(
      StringBuilder fieldsBuf, int openParenIndex, int numFields) {
    switch (numFields) {
      case 0:
        fieldsBuf.deleteCharAt(openParenIndex);
        break;
      case 1:
        fieldsBuf.setCharAt(openParenIndex, '/');
        break;
      default:
        fieldsBuf.append(')');
    }
  }

  /**
   * Compute the patch object of key/value pairs from the given original and patched objects, adding
   * a {@code @gd:fields} key for the fields mask.
   *
   * @param patched patched object
   * @param original original object
   * @return patch object of key/value pairs
   */
  public static Map<String, Object> computePatch(Object patched, Object original) {
    FieldsMask fieldsMask = new FieldsMask();
    ArrayMap<String, Object> result = computePatchInternal(fieldsMask, patched, original);
    if (fieldsMask.numDifferences != 0) {
      result.put("@gd:fields", fieldsMask.buf.toString());
    }
    return result;
  }

  private static ArrayMap<String, Object> computePatchInternal(
      FieldsMask fieldsMask, Object patchedObject, Object originalObject) {
    ArrayMap<String, Object> result = ArrayMap.create();
    Map<String, Object> patchedMap = Data.mapOf(patchedObject);
    Map<String, Object> originalMap = Data.mapOf(originalObject);
    TreeSet<String> fieldNames = new TreeSet<String>();
    fieldNames.addAll(patchedMap.keySet());
    fieldNames.addAll(originalMap.keySet());
    for (String name : fieldNames) {
      Object originalValue = originalMap.get(name);
      Object patchedValue = patchedMap.get(name);
      if (originalValue == patchedValue) {
        continue;
      }
      Class<?> type = originalValue == null ? patchedValue.getClass() : originalValue.getClass();
      if (Data.isPrimitive(type)) {
        if (originalValue != null && originalValue.equals(patchedValue)) {
          continue;
        }
        fieldsMask.append(name);
        // TODO(yanivi): wait for bug fix from server
        // if (!name.equals("text()") && name.charAt(0) != '@') {
        // fieldsMask.buf.append("/text()");
        // }
        if (patchedValue != null) {
          result.add(name, patchedValue);
        }
      } else if (Collection.class.isAssignableFrom(type)) {
        if (originalValue != null && patchedValue != null) {
          @SuppressWarnings("unchecked")
          Collection<Object> originalCollection = (Collection<Object>) originalValue;
          @SuppressWarnings("unchecked")
          Collection<Object> patchedCollection = (Collection<Object>) patchedValue;
          int size = originalCollection.size();
          if (size == patchedCollection.size()) {
            int i;
            for (i = 0; i < size; i++) {
              FieldsMask subFieldsMask = new FieldsMask();
              computePatchInternal(subFieldsMask, patchedValue, originalValue);
              if (subFieldsMask.numDifferences != 0) {
                break;
              }
            }
            if (i == size) {
              continue;
            }
          }
        }
        // TODO(yanivi): implement
        throw new UnsupportedOperationException(
            "not yet implemented: support for patching collections");
      } else {
        if (originalValue == null) { // TODO(yanivi): test
          fieldsMask.append(name);
          result.add(name, Data.mapOf(patchedValue));
        } else if (patchedValue == null) { // TODO(yanivi): test
          fieldsMask.append(name);
        } else {
          FieldsMask subFieldsMask = new FieldsMask();
          ArrayMap<String, Object> patch =
              computePatchInternal(subFieldsMask, patchedValue, originalValue);
          int numDifferences = subFieldsMask.numDifferences;
          if (numDifferences != 0) {
            fieldsMask.append(name, subFieldsMask);
            result.add(name, patch);
          }
        }
      }
    }
    return result;
  }

  static class FieldsMask {
    int numDifferences;
    StringBuilder buf = new StringBuilder();

    void append(String name) {
      StringBuilder buf = this.buf;
      if (++numDifferences != 1) {
        buf.append(',');
      }
      buf.append(name);
    }

    void append(String name, FieldsMask subFields) {
      append(name);
      StringBuilder buf = this.buf;
      boolean isSingle = subFields.numDifferences == 1;
      if (isSingle) {
        buf.append('/');
      } else {
        buf.append('(');
      }
      buf.append(subFields.buf);
      if (!isSingle) {
        buf.append(')');
      }
    }
  }

  private GoogleAtom() {
  }
}
