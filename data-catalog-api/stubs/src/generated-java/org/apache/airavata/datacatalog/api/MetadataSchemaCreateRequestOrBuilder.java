// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

public interface MetadataSchemaCreateRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MetadataSchemaCreateRequest)
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
   * <code>.MetadataSchema metadata_schema = 2;</code>
   * @return Whether the metadataSchema field is set.
   */
  boolean hasMetadataSchema();
  /**
   * <code>.MetadataSchema metadata_schema = 2;</code>
   * @return The metadataSchema.
   */
  org.apache.airavata.datacatalog.api.MetadataSchema getMetadataSchema();
  /**
   * <code>.MetadataSchema metadata_schema = 2;</code>
   */
  org.apache.airavata.datacatalog.api.MetadataSchemaOrBuilder getMetadataSchemaOrBuilder();
}
