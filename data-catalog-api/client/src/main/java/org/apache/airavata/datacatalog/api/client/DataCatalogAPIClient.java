package org.apache.airavata.datacatalog.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc;
import org.apache.airavata.datacatalog.api.DataProduct;
import org.apache.airavata.datacatalog.api.DataProductCreateRequest;
import org.apache.airavata.datacatalog.api.DataProductCreateResponse;
import org.apache.airavata.datacatalog.api.DataCatalogAPIServiceGrpc.DataCatalogAPIServiceBlockingStub;;

public class DataCatalogAPIClient {

    private static final Logger logger = LoggerFactory.getLogger(DataCatalogAPIClient.class);

    private final DataCatalogAPIServiceBlockingStub blockingStub;

    public DataCatalogAPIClient(Channel channel) {
        blockingStub = DataCatalogAPIServiceGrpc.newBlockingStub(channel);
    }

    public DataProduct createDataProduct(DataProduct dataProduct) {
        DataProductCreateRequest request = DataProductCreateRequest.newBuilder().setDataProduct(dataProduct).build();
        DataProductCreateResponse response = blockingStub.createDataProduct(request);
        return response.getDataProduct();
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:6565";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            DataCatalogAPIClient client = new DataCatalogAPIClient(channel);
            DataProduct dataProduct = DataProduct.newBuilder().setName("testing").build();
            DataProduct result = client.createDataProduct(dataProduct);
            System.out.println(MessageFormat.format("Created data product with id [{0}]", result.getDataProductId()));

        }finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
