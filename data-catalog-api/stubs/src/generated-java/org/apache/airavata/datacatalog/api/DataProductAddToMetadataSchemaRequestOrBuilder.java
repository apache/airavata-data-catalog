// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

public interface DataProductAddToMetadataSchemaRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:DataProductAddToMetadataSchemaRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.UserInfo user_info = 1;</code>
   * @return Whether the userInfo field is set.
   */
  boolean hasUserInfo();
  /**
   * <code>.UserInfo user_info = 1;</code>
   * @return The userInfo.
   */
  org.apache.airavata.datacatalog.api.UserInfo getUserInfo();
  /**
   * <code>.UserInfo user_info = 1;</code>
   */
  org.apache.airavata.datacatalog.api.UserInfoOrBuilder getUserInfoOrBuilder();

  /**
   * <code>string data_product_id = 2;</code>
   * @return The dataProductId.
   */
  java.lang.String getDataProductId();
  /**
   * <code>string data_product_id = 2;</code>
   * @return The bytes for dataProductId.
   */
  com.google.protobuf.ByteString
      getDataProductIdBytes();

  /**
   * <code>string schema_name = 3;</code>
   * @return The schemaName.
   */
  java.lang.String getSchemaName();
  /**
   * <code>string schema_name = 3;</code>
   * @return The bytes for schemaName.
   */
  com.google.protobuf.ByteString
      getSchemaNameBytes();
}
