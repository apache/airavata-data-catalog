// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DataCatalogAPI.proto
// Protobuf Java Version: 4.28.2

package org.apache.airavata.datacatalog.api;

public interface MetadataSchemaFieldCreateRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MetadataSchemaFieldCreateRequest)
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
   * <code>.MetadataSchemaField metadata_schema_field = 2;</code>
   * @return Whether the metadataSchemaField field is set.
   */
  boolean hasMetadataSchemaField();
  /**
   * <code>.MetadataSchemaField metadata_schema_field = 2;</code>
   * @return The metadataSchemaField.
   */
  org.apache.airavata.datacatalog.api.MetadataSchemaField getMetadataSchemaField();
  /**
   * <code>.MetadataSchemaField metadata_schema_field = 2;</code>
   */
  org.apache.airavata.datacatalog.api.MetadataSchemaFieldOrBuilder getMetadataSchemaFieldOrBuilder();
}
