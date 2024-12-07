// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

/**
 * Protobuf type {@code MetadataSchemaField}
 */
public final class MetadataSchemaField extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:MetadataSchemaField)
    MetadataSchemaFieldOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      MetadataSchemaField.class.getName());
  }
  // Use MetadataSchemaField.newBuilder() to construct.
  private MetadataSchemaField(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private MetadataSchemaField() {
    schemaName_ = "";
    fieldName_ = "";
    jsonPath_ = "";
    valueType_ = 0;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaField_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaField_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.apache.airavata.datacatalog.api.MetadataSchemaField.class, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder.class);
  }

  public static final int SCHEMA_NAME_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object schemaName_ = "";
  /**
   * <code>string schema_name = 1;</code>
   * @return The schemaName.
   */
  @java.lang.Override
  public java.lang.String getSchemaName() {
    java.lang.Object ref = schemaName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      schemaName_ = s;
      return s;
    }
  }
  /**
   * <code>string schema_name = 1;</code>
   * @return The bytes for schemaName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSchemaNameBytes() {
    java.lang.Object ref = schemaName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      schemaName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FIELD_NAME_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object fieldName_ = "";
  /**
   * <code>string field_name = 2;</code>
   * @return The fieldName.
   */
  @java.lang.Override
  public java.lang.String getFieldName() {
    java.lang.Object ref = fieldName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      fieldName_ = s;
      return s;
    }
  }
  /**
   * <code>string field_name = 2;</code>
   * @return The bytes for fieldName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getFieldNameBytes() {
    java.lang.Object ref = fieldName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      fieldName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int JSON_PATH_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object jsonPath_ = "";
  /**
   * <code>string json_path = 3;</code>
   * @return The jsonPath.
   */
  @java.lang.Override
  public java.lang.String getJsonPath() {
    java.lang.Object ref = jsonPath_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      jsonPath_ = s;
      return s;
    }
  }
  /**
   * <code>string json_path = 3;</code>
   * @return The bytes for jsonPath.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getJsonPathBytes() {
    java.lang.Object ref = jsonPath_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      jsonPath_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int VALUE_TYPE_FIELD_NUMBER = 4;
  private int valueType_ = 0;
  /**
   * <code>.FieldValueType value_type = 4;</code>
   * @return The enum numeric value on the wire for valueType.
   */
  @java.lang.Override public int getValueTypeValue() {
    return valueType_;
  }
  /**
   * <code>.FieldValueType value_type = 4;</code>
   * @return The valueType.
   */
  @java.lang.Override public org.apache.airavata.datacatalog.api.FieldValueType getValueType() {
    org.apache.airavata.datacatalog.api.FieldValueType result = org.apache.airavata.datacatalog.api.FieldValueType.forNumber(valueType_);
    return result == null ? org.apache.airavata.datacatalog.api.FieldValueType.UNRECOGNIZED : result;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(schemaName_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, schemaName_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(fieldName_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 2, fieldName_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(jsonPath_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, jsonPath_);
    }
    if (valueType_ != org.apache.airavata.datacatalog.api.FieldValueType.STRING.getNumber()) {
      output.writeEnum(4, valueType_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(schemaName_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, schemaName_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(fieldName_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(2, fieldName_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(jsonPath_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, jsonPath_);
    }
    if (valueType_ != org.apache.airavata.datacatalog.api.FieldValueType.STRING.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, valueType_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.apache.airavata.datacatalog.api.MetadataSchemaField)) {
      return super.equals(obj);
    }
    org.apache.airavata.datacatalog.api.MetadataSchemaField other = (org.apache.airavata.datacatalog.api.MetadataSchemaField) obj;

    if (!getSchemaName()
        .equals(other.getSchemaName())) return false;
    if (!getFieldName()
        .equals(other.getFieldName())) return false;
    if (!getJsonPath()
        .equals(other.getJsonPath())) return false;
    if (valueType_ != other.valueType_) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SCHEMA_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getSchemaName().hashCode();
    hash = (37 * hash) + FIELD_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getFieldName().hashCode();
    hash = (37 * hash) + JSON_PATH_FIELD_NUMBER;
    hash = (53 * hash) + getJsonPath().hashCode();
    hash = (37 * hash) + VALUE_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + valueType_;
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaField parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.apache.airavata.datacatalog.api.MetadataSchemaField prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code MetadataSchemaField}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:MetadataSchemaField)
      org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaField_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaField_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.apache.airavata.datacatalog.api.MetadataSchemaField.class, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder.class);
    }

    // Construct using org.apache.airavata.datacatalog.api.MetadataSchemaField.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      schemaName_ = "";
      fieldName_ = "";
      jsonPath_ = "";
      valueType_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaField_descriptor;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.MetadataSchemaField getDefaultInstanceForType() {
      return org.apache.airavata.datacatalog.api.MetadataSchemaField.getDefaultInstance();
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.MetadataSchemaField build() {
      org.apache.airavata.datacatalog.api.MetadataSchemaField result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.MetadataSchemaField buildPartial() {
      org.apache.airavata.datacatalog.api.MetadataSchemaField result = new org.apache.airavata.datacatalog.api.MetadataSchemaField(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(org.apache.airavata.datacatalog.api.MetadataSchemaField result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.schemaName_ = schemaName_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.fieldName_ = fieldName_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.jsonPath_ = jsonPath_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.valueType_ = valueType_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.apache.airavata.datacatalog.api.MetadataSchemaField) {
        return mergeFrom((org.apache.airavata.datacatalog.api.MetadataSchemaField)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.apache.airavata.datacatalog.api.MetadataSchemaField other) {
      if (other == org.apache.airavata.datacatalog.api.MetadataSchemaField.getDefaultInstance()) return this;
      if (!other.getSchemaName().isEmpty()) {
        schemaName_ = other.schemaName_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.getFieldName().isEmpty()) {
        fieldName_ = other.fieldName_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (!other.getJsonPath().isEmpty()) {
        jsonPath_ = other.jsonPath_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (other.valueType_ != 0) {
        setValueTypeValue(other.getValueTypeValue());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              schemaName_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              fieldName_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              jsonPath_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 32: {
              valueType_ = input.readEnum();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private java.lang.Object schemaName_ = "";
    /**
     * <code>string schema_name = 1;</code>
     * @return The schemaName.
     */
    public java.lang.String getSchemaName() {
      java.lang.Object ref = schemaName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        schemaName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string schema_name = 1;</code>
     * @return The bytes for schemaName.
     */
    public com.google.protobuf.ByteString
        getSchemaNameBytes() {
      java.lang.Object ref = schemaName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        schemaName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string schema_name = 1;</code>
     * @param value The schemaName to set.
     * @return This builder for chaining.
     */
    public Builder setSchemaName(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      schemaName_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>string schema_name = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSchemaName() {
      schemaName_ = getDefaultInstance().getSchemaName();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>string schema_name = 1;</code>
     * @param value The bytes for schemaName to set.
     * @return This builder for chaining.
     */
    public Builder setSchemaNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      schemaName_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private java.lang.Object fieldName_ = "";
    /**
     * <code>string field_name = 2;</code>
     * @return The fieldName.
     */
    public java.lang.String getFieldName() {
      java.lang.Object ref = fieldName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fieldName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string field_name = 2;</code>
     * @return The bytes for fieldName.
     */
    public com.google.protobuf.ByteString
        getFieldNameBytes() {
      java.lang.Object ref = fieldName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        fieldName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string field_name = 2;</code>
     * @param value The fieldName to set.
     * @return This builder for chaining.
     */
    public Builder setFieldName(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      fieldName_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>string field_name = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearFieldName() {
      fieldName_ = getDefaultInstance().getFieldName();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>string field_name = 2;</code>
     * @param value The bytes for fieldName to set.
     * @return This builder for chaining.
     */
    public Builder setFieldNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      fieldName_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.lang.Object jsonPath_ = "";
    /**
     * <code>string json_path = 3;</code>
     * @return The jsonPath.
     */
    public java.lang.String getJsonPath() {
      java.lang.Object ref = jsonPath_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        jsonPath_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string json_path = 3;</code>
     * @return The bytes for jsonPath.
     */
    public com.google.protobuf.ByteString
        getJsonPathBytes() {
      java.lang.Object ref = jsonPath_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        jsonPath_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string json_path = 3;</code>
     * @param value The jsonPath to set.
     * @return This builder for chaining.
     */
    public Builder setJsonPath(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      jsonPath_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>string json_path = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearJsonPath() {
      jsonPath_ = getDefaultInstance().getJsonPath();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <code>string json_path = 3;</code>
     * @param value The bytes for jsonPath to set.
     * @return This builder for chaining.
     */
    public Builder setJsonPathBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      jsonPath_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private int valueType_ = 0;
    /**
     * <code>.FieldValueType value_type = 4;</code>
     * @return The enum numeric value on the wire for valueType.
     */
    @java.lang.Override public int getValueTypeValue() {
      return valueType_;
    }
    /**
     * <code>.FieldValueType value_type = 4;</code>
     * @param value The enum numeric value on the wire for valueType to set.
     * @return This builder for chaining.
     */
    public Builder setValueTypeValue(int value) {
      valueType_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>.FieldValueType value_type = 4;</code>
     * @return The valueType.
     */
    @java.lang.Override
    public org.apache.airavata.datacatalog.api.FieldValueType getValueType() {
      org.apache.airavata.datacatalog.api.FieldValueType result = org.apache.airavata.datacatalog.api.FieldValueType.forNumber(valueType_);
      return result == null ? org.apache.airavata.datacatalog.api.FieldValueType.UNRECOGNIZED : result;
    }
    /**
     * <code>.FieldValueType value_type = 4;</code>
     * @param value The valueType to set.
     * @return This builder for chaining.
     */
    public Builder setValueType(org.apache.airavata.datacatalog.api.FieldValueType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000008;
      valueType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.FieldValueType value_type = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearValueType() {
      bitField0_ = (bitField0_ & ~0x00000008);
      valueType_ = 0;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:MetadataSchemaField)
  }

  // @@protoc_insertion_point(class_scope:MetadataSchemaField)
  private static final org.apache.airavata.datacatalog.api.MetadataSchemaField DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.apache.airavata.datacatalog.api.MetadataSchemaField();
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaField getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MetadataSchemaField>
      PARSER = new com.google.protobuf.AbstractParser<MetadataSchemaField>() {
    @java.lang.Override
    public MetadataSchemaField parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<MetadataSchemaField> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MetadataSchemaField> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.apache.airavata.datacatalog.api.MetadataSchemaField getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

