// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

/**
 * Protobuf type {@code DataProductUpdateResponse}
 */
public final class DataProductUpdateResponse extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:DataProductUpdateResponse)
    DataProductUpdateResponseOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      DataProductUpdateResponse.class.getName());
  }
  // Use DataProductUpdateResponse.newBuilder() to construct.
  private DataProductUpdateResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private DataProductUpdateResponse() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductUpdateResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductUpdateResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.apache.airavata.datacatalog.api.DataProductUpdateResponse.class, org.apache.airavata.datacatalog.api.DataProductUpdateResponse.Builder.class);
  }

  private int bitField0_;
  public static final int DATA_PRODUCT_FIELD_NUMBER = 1;
  private org.apache.airavata.datacatalog.api.DataProduct dataProduct_;
  /**
   * <code>.DataProduct data_product = 1;</code>
   * @return Whether the dataProduct field is set.
   */
  @java.lang.Override
  public boolean hasDataProduct() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.DataProduct data_product = 1;</code>
   * @return The dataProduct.
   */
  @java.lang.Override
  public org.apache.airavata.datacatalog.api.DataProduct getDataProduct() {
    return dataProduct_ == null ? org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance() : dataProduct_;
  }
  /**
   * <code>.DataProduct data_product = 1;</code>
   */
  @java.lang.Override
  public org.apache.airavata.datacatalog.api.DataProductOrBuilder getDataProductOrBuilder() {
    return dataProduct_ == null ? org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance() : dataProduct_;
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
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getDataProduct());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getDataProduct());
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
    if (!(obj instanceof org.apache.airavata.datacatalog.api.DataProductUpdateResponse)) {
      return super.equals(obj);
    }
    org.apache.airavata.datacatalog.api.DataProductUpdateResponse other = (org.apache.airavata.datacatalog.api.DataProductUpdateResponse) obj;

    if (hasDataProduct() != other.hasDataProduct()) return false;
    if (hasDataProduct()) {
      if (!getDataProduct()
          .equals(other.getDataProduct())) return false;
    }
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
    if (hasDataProduct()) {
      hash = (37 * hash) + DATA_PRODUCT_FIELD_NUMBER;
      hash = (53 * hash) + getDataProduct().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse parseFrom(
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
  public static Builder newBuilder(org.apache.airavata.datacatalog.api.DataProductUpdateResponse prototype) {
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
   * Protobuf type {@code DataProductUpdateResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:DataProductUpdateResponse)
      org.apache.airavata.datacatalog.api.DataProductUpdateResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductUpdateResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductUpdateResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.apache.airavata.datacatalog.api.DataProductUpdateResponse.class, org.apache.airavata.datacatalog.api.DataProductUpdateResponse.Builder.class);
    }

    // Construct using org.apache.airavata.datacatalog.api.DataProductUpdateResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage
              .alwaysUseFieldBuilders) {
        getDataProductFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      dataProduct_ = null;
      if (dataProductBuilder_ != null) {
        dataProductBuilder_.dispose();
        dataProductBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.apache.airavata.datacatalog.api.DataCatalogAPI.internal_static_DataProductUpdateResponse_descriptor;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.DataProductUpdateResponse getDefaultInstanceForType() {
      return org.apache.airavata.datacatalog.api.DataProductUpdateResponse.getDefaultInstance();
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.DataProductUpdateResponse build() {
      org.apache.airavata.datacatalog.api.DataProductUpdateResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.apache.airavata.datacatalog.api.DataProductUpdateResponse buildPartial() {
      org.apache.airavata.datacatalog.api.DataProductUpdateResponse result = new org.apache.airavata.datacatalog.api.DataProductUpdateResponse(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(org.apache.airavata.datacatalog.api.DataProductUpdateResponse result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.dataProduct_ = dataProductBuilder_ == null
            ? dataProduct_
            : dataProductBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.apache.airavata.datacatalog.api.DataProductUpdateResponse) {
        return mergeFrom((org.apache.airavata.datacatalog.api.DataProductUpdateResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.apache.airavata.datacatalog.api.DataProductUpdateResponse other) {
      if (other == org.apache.airavata.datacatalog.api.DataProductUpdateResponse.getDefaultInstance()) return this;
      if (other.hasDataProduct()) {
        mergeDataProduct(other.getDataProduct());
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
              input.readMessage(
                  getDataProductFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
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

    private org.apache.airavata.datacatalog.api.DataProduct dataProduct_;
    private com.google.protobuf.SingleFieldBuilder<
        org.apache.airavata.datacatalog.api.DataProduct, org.apache.airavata.datacatalog.api.DataProduct.Builder, org.apache.airavata.datacatalog.api.DataProductOrBuilder> dataProductBuilder_;
    /**
     * <code>.DataProduct data_product = 1;</code>
     * @return Whether the dataProduct field is set.
     */
    public boolean hasDataProduct() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     * @return The dataProduct.
     */
    public org.apache.airavata.datacatalog.api.DataProduct getDataProduct() {
      if (dataProductBuilder_ == null) {
        return dataProduct_ == null ? org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance() : dataProduct_;
      } else {
        return dataProductBuilder_.getMessage();
      }
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    public Builder setDataProduct(org.apache.airavata.datacatalog.api.DataProduct value) {
      if (dataProductBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        dataProduct_ = value;
      } else {
        dataProductBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    public Builder setDataProduct(
        org.apache.airavata.datacatalog.api.DataProduct.Builder builderForValue) {
      if (dataProductBuilder_ == null) {
        dataProduct_ = builderForValue.build();
      } else {
        dataProductBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    public Builder mergeDataProduct(org.apache.airavata.datacatalog.api.DataProduct value) {
      if (dataProductBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          dataProduct_ != null &&
          dataProduct_ != org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance()) {
          getDataProductBuilder().mergeFrom(value);
        } else {
          dataProduct_ = value;
        }
      } else {
        dataProductBuilder_.mergeFrom(value);
      }
      if (dataProduct_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    public Builder clearDataProduct() {
      bitField0_ = (bitField0_ & ~0x00000001);
      dataProduct_ = null;
      if (dataProductBuilder_ != null) {
        dataProductBuilder_.dispose();
        dataProductBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProduct.Builder getDataProductBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getDataProductFieldBuilder().getBuilder();
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    public org.apache.airavata.datacatalog.api.DataProductOrBuilder getDataProductOrBuilder() {
      if (dataProductBuilder_ != null) {
        return dataProductBuilder_.getMessageOrBuilder();
      } else {
        return dataProduct_ == null ?
            org.apache.airavata.datacatalog.api.DataProduct.getDefaultInstance() : dataProduct_;
      }
    }
    /**
     * <code>.DataProduct data_product = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        org.apache.airavata.datacatalog.api.DataProduct, org.apache.airavata.datacatalog.api.DataProduct.Builder, org.apache.airavata.datacatalog.api.DataProductOrBuilder> 
        getDataProductFieldBuilder() {
      if (dataProductBuilder_ == null) {
        dataProductBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            org.apache.airavata.datacatalog.api.DataProduct, org.apache.airavata.datacatalog.api.DataProduct.Builder, org.apache.airavata.datacatalog.api.DataProductOrBuilder>(
                getDataProduct(),
                getParentForChildren(),
                isClean());
        dataProduct_ = null;
      }
      return dataProductBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:DataProductUpdateResponse)
  }

  // @@protoc_insertion_point(class_scope:DataProductUpdateResponse)
  private static final org.apache.airavata.datacatalog.api.DataProductUpdateResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.apache.airavata.datacatalog.api.DataProductUpdateResponse();
  }

  public static org.apache.airavata.datacatalog.api.DataProductUpdateResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DataProductUpdateResponse>
      PARSER = new com.google.protobuf.AbstractParser<DataProductUpdateResponse>() {
    @java.lang.Override
    public DataProductUpdateResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<DataProductUpdateResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DataProductUpdateResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.apache.airavata.datacatalog.api.DataProductUpdateResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

