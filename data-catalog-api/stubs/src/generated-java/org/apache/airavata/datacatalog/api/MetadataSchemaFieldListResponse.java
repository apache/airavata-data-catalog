// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

/**
 * Protobuf type {@code MetadataSchemaFieldListResponse}
 */
public final class MetadataSchemaFieldListResponse extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:MetadataSchemaFieldListResponse)
    MetadataSchemaFieldListResponseOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      MetadataSchemaFieldListResponse.class.getName());
  }
  // Use MetadataSchemaFieldListResponse.newBuilder() to construct.
  private MetadataSchemaFieldListResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private MetadataSchemaFieldListResponse() {
    metadataSchemaFields_ = java.util.Collections.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaFieldListResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaFieldListResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.class, org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.Builder.class);
  }

  public static final int METADATA_SCHEMA_FIELDS_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<org.apache.airavata.datacatalog.api.MetadataSchemaField> metadataSchemaFields_;
  /**
   * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
   */
  @java.lang.Override
  public java.util.List<org.apache.airavata.datacatalog.api.MetadataSchemaField> getMetadataSchemaFieldsList() {
    return metadataSchemaFields_;
  }
  /**
   * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder> 
      getMetadataSchemaFieldsOrBuilderList() {
    return metadataSchemaFields_;
  }
  /**
   * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
   */
  @java.lang.Override
  public int getMetadataSchemaFieldsCount() {
    return metadataSchemaFields_.size();
  }
  /**
   * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
   */
  @java.lang.Override
  public org.apache.airavata.datacatalog.api.MetadataSchemaField getMetadataSchemaFields(int index) {
    return metadataSchemaFields_.get(index);
  }
  /**
   * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
   */
  @java.lang.Override
  public org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder getMetadataSchemaFieldsOrBuilder(
      int index) {
    return metadataSchemaFields_.get(index);
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
    for (int i = 0; i < metadataSchemaFields_.size(); i++) {
      output.writeMessage(1, metadataSchemaFields_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < metadataSchemaFields_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, metadataSchemaFields_.get(i));
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
    if (!(obj instanceof org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse)) {
      return super.equals(obj);
    }
    org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse other = (org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse) obj;

    if (!getMetadataSchemaFieldsList()
        .equals(other.getMetadataSchemaFieldsList())) return false;
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
    if (getMetadataSchemaFieldsCount() > 0) {
      hash = (37 * hash) + METADATA_SCHEMA_FIELDS_FIELD_NUMBER;
      hash = (53 * hash) + getMetadataSchemaFieldsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse parseFrom(
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
  public static Builder newBuilder(org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse prototype) {
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
   * Protobuf type {@code MetadataSchemaFieldListResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:MetadataSchemaFieldListResponse)
      org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaFieldListResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaFieldListResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.class, org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.Builder.class);
    }

    // Construct using org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.newBuilder()
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
      if (metadataSchemaFieldsBuilder_ == null) {
        metadataSchemaFields_ = java.util.Collections.emptyList();
      } else {
        metadataSchemaFields_ = null;
        metadataSchemaFieldsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_MetadataSchemaFieldListResponse_descriptor;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse getDefaultInstanceForType() {
      return org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.getDefaultInstance();
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse build() {
      org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse buildPartial() {
      org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse result = new org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse result) {
      if (metadataSchemaFieldsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          metadataSchemaFields_ = java.util.Collections.unmodifiableList(metadataSchemaFields_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.metadataSchemaFields_ = metadataSchemaFields_;
      } else {
        result.metadataSchemaFields_ = metadataSchemaFieldsBuilder_.build();
      }
    }

    private void buildPartial0(org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse) {
        return mergeFrom((org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse other) {
      if (other == org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse.getDefaultInstance()) return this;
      if (metadataSchemaFieldsBuilder_ == null) {
        if (!other.metadataSchemaFields_.isEmpty()) {
          if (metadataSchemaFields_.isEmpty()) {
            metadataSchemaFields_ = other.metadataSchemaFields_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMetadataSchemaFieldsIsMutable();
            metadataSchemaFields_.addAll(other.metadataSchemaFields_);
          }
          onChanged();
        }
      } else {
        if (!other.metadataSchemaFields_.isEmpty()) {
          if (metadataSchemaFieldsBuilder_.isEmpty()) {
            metadataSchemaFieldsBuilder_.dispose();
            metadataSchemaFieldsBuilder_ = null;
            metadataSchemaFields_ = other.metadataSchemaFields_;
            bitField0_ = (bitField0_ & ~0x00000001);
            metadataSchemaFieldsBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getMetadataSchemaFieldsFieldBuilder() : null;
          } else {
            metadataSchemaFieldsBuilder_.addAllMessages(other.metadataSchemaFields_);
          }
        }
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
              org.apache.airavata.datacatalog.api.MetadataSchemaField m =
                  input.readMessage(
                      org.apache.airavata.datacatalog.api.MetadataSchemaField.parser(),
                      extensionRegistry);
              if (metadataSchemaFieldsBuilder_ == null) {
                ensureMetadataSchemaFieldsIsMutable();
                metadataSchemaFields_.add(m);
              } else {
                metadataSchemaFieldsBuilder_.addMessage(m);
              }
              break;
            } // case 10
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

    private java.util.List<org.apache.airavata.datacatalog.api.MetadataSchemaField> metadataSchemaFields_ =
      java.util.Collections.emptyList();
    private void ensureMetadataSchemaFieldsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        metadataSchemaFields_ = new java.util.ArrayList<org.apache.airavata.datacatalog.api.MetadataSchemaField>(metadataSchemaFields_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        org.apache.airavata.datacatalog.api.MetadataSchemaField, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder, org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder> metadataSchemaFieldsBuilder_;

    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public java.util.List<org.apache.airavata.datacatalog.api.MetadataSchemaField> getMetadataSchemaFieldsList() {
      if (metadataSchemaFieldsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(metadataSchemaFields_);
      } else {
        return metadataSchemaFieldsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public int getMetadataSchemaFieldsCount() {
      if (metadataSchemaFieldsBuilder_ == null) {
        return metadataSchemaFields_.size();
      } else {
        return metadataSchemaFieldsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.MetadataSchemaField getMetadataSchemaFields(int index) {
      if (metadataSchemaFieldsBuilder_ == null) {
        return metadataSchemaFields_.get(index);
      } else {
        return metadataSchemaFieldsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder setMetadataSchemaFields(
        int index, org.apache.airavata.datacatalog.api.MetadataSchemaField value) {
      if (metadataSchemaFieldsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.set(index, value);
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder setMetadataSchemaFields(
        int index, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder builderForValue) {
      if (metadataSchemaFieldsBuilder_ == null) {
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.set(index, builderForValue.build());
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder addMetadataSchemaFields(org.apache.airavata.datacatalog.api.MetadataSchemaField value) {
      if (metadataSchemaFieldsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.add(value);
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder addMetadataSchemaFields(
        int index, org.apache.airavata.datacatalog.api.MetadataSchemaField value) {
      if (metadataSchemaFieldsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.add(index, value);
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder addMetadataSchemaFields(
        org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder builderForValue) {
      if (metadataSchemaFieldsBuilder_ == null) {
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.add(builderForValue.build());
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder addMetadataSchemaFields(
        int index, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder builderForValue) {
      if (metadataSchemaFieldsBuilder_ == null) {
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.add(index, builderForValue.build());
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder addAllMetadataSchemaFields(
        java.lang.Iterable<? extends org.apache.airavata.datacatalog.api.MetadataSchemaField> values) {
      if (metadataSchemaFieldsBuilder_ == null) {
        ensureMetadataSchemaFieldsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, metadataSchemaFields_);
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder clearMetadataSchemaFields() {
      if (metadataSchemaFieldsBuilder_ == null) {
        metadataSchemaFields_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public Builder removeMetadataSchemaFields(int index) {
      if (metadataSchemaFieldsBuilder_ == null) {
        ensureMetadataSchemaFieldsIsMutable();
        metadataSchemaFields_.remove(index);
        onChanged();
      } else {
        metadataSchemaFieldsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder getMetadataSchemaFieldsBuilder(
        int index) {
      return getMetadataSchemaFieldsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder getMetadataSchemaFieldsOrBuilder(
        int index) {
      if (metadataSchemaFieldsBuilder_ == null) {
        return metadataSchemaFields_.get(index);  } else {
        return metadataSchemaFieldsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public java.util.List<? extends org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder> 
         getMetadataSchemaFieldsOrBuilderList() {
      if (metadataSchemaFieldsBuilder_ != null) {
        return metadataSchemaFieldsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(metadataSchemaFields_);
      }
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder addMetadataSchemaFieldsBuilder() {
      return getMetadataSchemaFieldsFieldBuilder().addBuilder(
          org.apache.airavata.datacatalog.api.MetadataSchemaField.getDefaultInstance());
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder addMetadataSchemaFieldsBuilder(
        int index) {
      return getMetadataSchemaFieldsFieldBuilder().addBuilder(
          index, org.apache.airavata.datacatalog.api.MetadataSchemaField.getDefaultInstance());
    }
    /**
     * <code>repeated .MetadataSchemaField metadata_schema_fields = 1;</code>
     */
    public java.util.List<org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder> 
         getMetadataSchemaFieldsBuilderList() {
      return getMetadataSchemaFieldsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        org.apache.airavata.datacatalog.api.MetadataSchemaField, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder, org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder> 
        getMetadataSchemaFieldsFieldBuilder() {
      if (metadataSchemaFieldsBuilder_ == null) {
        metadataSchemaFieldsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            org.apache.airavata.datacatalog.api.MetadataSchemaField, org.apache.airavata.datacatalog.api.MetadataSchemaField.Builder, org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder>(
                metadataSchemaFields_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        metadataSchemaFields_ = null;
      }
      return metadataSchemaFieldsBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:MetadataSchemaFieldListResponse)
  }

  // @@protoc_insertion_point(class_scope:MetadataSchemaFieldListResponse)
  private static final org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse();
  }

  public static org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MetadataSchemaFieldListResponse>
      PARSER = new com.google.protobuf.AbstractParser<MetadataSchemaFieldListResponse>() {
    @java.lang.Override
    public MetadataSchemaFieldListResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<MetadataSchemaFieldListResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MetadataSchemaFieldListResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.apache.airavata.datacatalog.api.MetadataSchemaFieldListResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

