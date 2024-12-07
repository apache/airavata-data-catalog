// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

/**
 * Protobuf type {@code DataProductSearchResponse}
 */
public final class DataProductSearchResponse extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:DataProductSearchResponse)
    DataProductSearchResponseOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      DataProductSearchResponse.class.getName());
  }
  // Use DataProductSearchResponse.newBuilder() to construct.
  private DataProductSearchResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private DataProductSearchResponse() {
    dataProducts_ = java.util.Collections.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductSearchResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductSearchResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.apache.airavata.datacatalog.api.DataProductSearchResponse.class, org.apache.airavata.datacatalog.api.DataProductSearchResponse.Builder.class);
  }

  public static final int DATA_PRODUCTS_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<org.apache.airavata.datacatalog.api.DataProduct> dataProducts_;
  /**
   * <code>repeated .DataProduct data_products = 1;</code>
   */
  @java.lang.Override
  public java.util.List<org.apache.airavata.datacatalog.api.DataProduct> getDataProductsList() {
    return dataProducts_;
  }
  /**
   * <code>repeated .DataProduct data_products = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends org.apache.airavata.datacatalog.api.DataProductOrBuilder> 
      getDataProductsOrBuilderList() {
    return dataProducts_;
  }
  /**
   * <code>repeated .DataProduct data_products = 1;</code>
   */
  @java.lang.Override
  public int getDataProductsCount() {
    return dataProducts_.size();
  }
  /**
   * <code>repeated .DataProduct data_products = 1;</code>
   */
  @java.lang.Override
  public org.apache.airavata.datacatalog.api.DataProduct getDataProducts(int index) {
    return dataProducts_.get(index);
  }
  /**
   * <code>repeated .DataProduct data_products = 1;</code>
   */
  @java.lang.Override
  public org.apache.airavata.datacatalog.api.DataProductOrBuilder getDataProductsOrBuilder(
      int index) {
    return dataProducts_.get(index);
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
    for (int i = 0; i < dataProducts_.size(); i++) {
      output.writeMessage(1, dataProducts_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < dataProducts_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, dataProducts_.get(i));
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
    if (!(obj instanceof org.apache.airavata.datacatalog.api.DataProductSearchResponse)) {
      return super.equals(obj);
    }
    org.apache.airavata.datacatalog.api.DataProductSearchResponse other = (org.apache.airavata.datacatalog.api.DataProductSearchResponse) obj;

    if (!getDataProductsList()
        .equals(other.getDataProductsList())) return false;
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
    if (getDataProductsCount() > 0) {
      hash = (37 * hash) + DATA_PRODUCTS_FIELD_NUMBER;
      hash = (53 * hash) + getDataProductsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse parseFrom(
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
  public static Builder newBuilder(org.apache.airavata.datacatalog.api.DataProductSearchResponse prototype) {
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
   * Protobuf type {@code DataProductSearchResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:DataProductSearchResponse)
      org.apache.airavata.datacatalog.api.DataProductSearchResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductSearchResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductSearchResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.apache.airavata.datacatalog.api.DataProductSearchResponse.class, org.apache.airavata.datacatalog.api.DataProductSearchResponse.Builder.class);
    }

    // Construct using org.apache.airavata.datacatalog.api.DataProductSearchResponse.newBuilder()
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
      if (dataProductsBuilder_ == null) {
        dataProducts_ = java.util.Collections.emptyList();
      } else {
        dataProducts_ = null;
        dataProductsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductSearchResponse_descriptor;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.DataProductSearchResponse getDefaultInstanceForType() {
      return org.apache.airavata.datacatalog.api.DataProductSearchResponse.getDefaultInstance();
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.DataProductSearchResponse build() {
      org.apache.airavata.datacatalog.api.DataProductSearchResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.DataProductSearchResponse buildPartial() {
      org.apache.airavata.datacatalog.api.DataProductSearchResponse result = new org.apache.airavata.datacatalog.api.DataProductSearchResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(org.apache.airavata.datacatalog.api.DataProductSearchResponse result) {
      if (dataProductsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          dataProducts_ = java.util.Collections.unmodifiableList(dataProducts_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.dataProducts_ = dataProducts_;
      } else {
        result.dataProducts_ = dataProductsBuilder_.build();
      }
    }

    private void buildPartial0(org.apache.airavata.datacatalog.api.DataProductSearchResponse result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.apache.airavata.datacatalog.api.DataProductSearchResponse) {
        return mergeFrom((org.apache.airavata.datacatalog.api.DataProductSearchResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.apache.airavata.datacatalog.api.DataProductSearchResponse other) {
      if (other == org.apache.airavata.datacatalog.api.DataProductSearchResponse.getDefaultInstance()) return this;
      if (dataProductsBuilder_ == null) {
        if (!other.dataProducts_.isEmpty()) {
          if (dataProducts_.isEmpty()) {
            dataProducts_ = other.dataProducts_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureDataProductsIsMutable();
            dataProducts_.addAll(other.dataProducts_);
          }
          onChanged();
        }
      } else {
        if (!other.dataProducts_.isEmpty()) {
          if (dataProductsBuilder_.isEmpty()) {
            dataProductsBuilder_.dispose();
            dataProductsBuilder_ = null;
            dataProducts_ = other.dataProducts_;
            bitField0_ = (bitField0_ & ~0x00000001);
            dataProductsBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getDataProductsFieldBuilder() : null;
          } else {
            dataProductsBuilder_.addAllMessages(other.dataProducts_);
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
              org.apache.airavata.datacatalog.api.DataProduct m =
                  input.readMessage(
                      org.apache.airavata.datacatalog.api.DataProduct.parser(),
                      extensionRegistry);
              if (dataProductsBuilder_ == null) {
                ensureDataProductsIsMutable();
                dataProducts_.add(m);
              } else {
                dataProductsBuilder_.addMessage(m);
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

    private java.util.List<org.apache.airavata.datacatalog.api.DataProduct> dataProducts_ =
      java.util.Collections.emptyList();
    private void ensureDataProductsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        dataProducts_ = new java.util.ArrayList<org.apache.airavata.datacatalog.api.DataProduct>(dataProducts_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        org.apache.airavata.datacatalog.api.DataProduct, org.apache.airavata.datacatalog.api.DataProduct.Builder, org.apache.airavata.datacatalog.api.DataProductOrBuilder> dataProductsBuilder_;

    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public java.util.List<org.apache.airavata.datacatalog.api.DataProduct> getDataProductsList() {
      if (dataProductsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(dataProducts_);
      } else {
        return dataProductsBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public int getDataProductsCount() {
      if (dataProductsBuilder_ == null) {
        return dataProducts_.size();
      } else {
        return dataProductsBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProduct getDataProducts(int index) {
      if (dataProductsBuilder_ == null) {
        return dataProducts_.get(index);
      } else {
        return dataProductsBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder setDataProducts(
        int index, org.apache.airavata.datacatalog.api.DataProduct value) {
      if (dataProductsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDataProductsIsMutable();
        dataProducts_.set(index, value);
        onChanged();
      } else {
        dataProductsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder setDataProducts(
        int index, org.apache.airavata.datacatalog.api.DataProduct.Builder builderForValue) {
      if (dataProductsBuilder_ == null) {
        ensureDataProductsIsMutable();
        dataProducts_.set(index, builderForValue.build());
        onChanged();
      } else {
        dataProductsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder addDataProducts(org.apache.airavata.datacatalog.api.DataProduct value) {
      if (dataProductsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDataProductsIsMutable();
        dataProducts_.add(value);
        onChanged();
      } else {
        dataProductsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder addDataProducts(
        int index, org.apache.airavata.datacatalog.api.DataProduct value) {
      if (dataProductsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDataProductsIsMutable();
        dataProducts_.add(index, value);
        onChanged();
      } else {
        dataProductsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder addDataProducts(
        org.apache.airavata.datacatalog.api.DataProduct.Builder builderForValue) {
      if (dataProductsBuilder_ == null) {
        ensureDataProductsIsMutable();
        dataProducts_.add(builderForValue.build());
        onChanged();
      } else {
        dataProductsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder addDataProducts(
        int index, org.apache.airavata.datacatalog.api.DataProduct.Builder builderForValue) {
      if (dataProductsBuilder_ == null) {
        ensureDataProductsIsMutable();
        dataProducts_.add(index, builderForValue.build());
        onChanged();
      } else {
        dataProductsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder addAllDataProducts(
        java.lang.Iterable<? extends org.apache.airavata.datacatalog.api.DataProduct> values) {
      if (dataProductsBuilder_ == null) {
        ensureDataProductsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, dataProducts_);
        onChanged();
      } else {
        dataProductsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder clearDataProducts() {
      if (dataProductsBuilder_ == null) {
        dataProducts_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        dataProductsBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public Builder removeDataProducts(int index) {
      if (dataProductsBuilder_ == null) {
        ensureDataProductsIsMutable();
        dataProducts_.remove(index);
        onChanged();
      } else {
        dataProductsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProduct.Builder getDataProductsBuilder(
        int index) {
      return getDataProductsFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProductOrBuilder getDataProductsOrBuilder(
        int index) {
      if (dataProductsBuilder_ == null) {
        return dataProducts_.get(index);  } else {
        return dataProductsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public java.util.List<? extends org.apache.airavata.datacatalog.api.DataProductOrBuilder> 
         getDataProductsOrBuilderList() {
      if (dataProductsBuilder_ != null) {
        return dataProductsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(dataProducts_);
      }
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProduct.Builder addDataProductsBuilder() {
      return getDataProductsFieldBuilder().addBuilder(
          org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance());
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProduct.Builder addDataProductsBuilder(
        int index) {
      return getDataProductsFieldBuilder().addBuilder(
          index, org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance());
    }
    /**
     * <code>repeated .DataProduct data_products = 1;</code>
     */
    public java.util.List<org.apache.airavata.datacatalog.api.DataProduct.Builder> 
         getDataProductsBuilderList() {
      return getDataProductsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        org.apache.airavata.datacatalog.api.DataProduct, org.apache.airavata.datacatalog.api.DataProduct.Builder, org.apache.airavata.datacatalog.api.DataProductOrBuilder> 
        getDataProductsFieldBuilder() {
      if (dataProductsBuilder_ == null) {
        dataProductsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            org.apache.airavata.datacatalog.api.DataProduct, org.apache.airavata.datacatalog.api.DataProduct.Builder, org.apache.airavata.datacatalog.api.DataProductOrBuilder>(
                dataProducts_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        dataProducts_ = null;
      }
      return dataProductsBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:DataProductSearchResponse)
  }

  // @@protoc_insertion_point(class_scope:DataProductSearchResponse)
  private static final org.apache.airavata.datacatalog.api.DataProductSearchResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.apache.airavata.datacatalog.api.DataProductSearchResponse();
  }

  public static org.apache.airavata.datacatalog.api.DataProductSearchResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DataProductSearchResponse>
      PARSER = new com.google.protobuf.AbstractParser<DataProductSearchResponse>() {
    @java.lang.Override
    public DataProductSearchResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<DataProductSearchResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DataProductSearchResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.apache.airavata.datacatalog.api.DataProductSearchResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

