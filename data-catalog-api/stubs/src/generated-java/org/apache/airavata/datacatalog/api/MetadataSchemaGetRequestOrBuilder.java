// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

public interface MetadataSchemaGetRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MetadataSchemaGetRequest)
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
   * <code>string schema_name = 2;</code>
   * @return The schemaName.
   */
  java.lang.String getSchemaName();
  /**
   * <code>string schema_name = 2;</code>
   * @return The bytes for schemaName.
   */
  com.google.protobuf.ByteString
      getSchemaNameBytes();
}
