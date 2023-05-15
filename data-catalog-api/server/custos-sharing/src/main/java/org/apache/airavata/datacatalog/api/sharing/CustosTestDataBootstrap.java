package org.apache.airavata.datacatalog.api.sharing;

import java.io.IOException;

import org.apache.custos.clients.CustosClientProvider;
import org.apache.custos.iam.service.FindUsersResponse;
import org.apache.custos.user.management.client.UserManagementClient;

public class CustosTestDataBootstrap {
    public static void main(String[] args) throws IOException {

        // Super tenant
        // String clientId = System.getenv("CUSTOS_SUPER_CLIENT_ID");
        // String clientSec = System.getenv("CUSTOS_SUPER_CLIENT_SEC");
        // String childClientId = System.getenv("CUSTOS_CLIENT_ID");

        String clientId = System.getenv("CUSTOS_CLIENT_ID");
        String clientSec = System.getenv("CUSTOS_CLIENT_SEC");

        CustosClientProvider custosClientProvider = new CustosClientProvider.Builder().setServerHost("localhost")
                .setServerPort(7000)
                .setClientId(clientId) // client Id generated from above step or any active tenant id
                .setClientSec(clientSec)
                .usePlainText(true) // Don't use this in production setup
                .build();
        UserManagementClient userManagementClient = custosClientProvider.getUserManagementClient();
        String testUsername = "demouser";
        FindUsersResponse findUsersResponse = userManagementClient.findUser(testUsername, null, null, null, 0, 1);
        if (findUsersResponse.getUsersCount() == 0) {
            // Create an enable a demo user
            System.out.println("Creating " + testUsername + " user in client " + clientId);
            userManagementClient.registerUser(testUsername, "Demo", "User",
                    "testpassword", "demouser@gmail.com", false);
            userManagementClient.enableUser(testUsername);
            // Should find it now
            findUsersResponse = userManagementClient.findUser(testUsername, null, null, null, 0, 1);
        }
        // Super tenant user query
        // FindUsersResponse findUsersResponse =
        // userManagementClient.findUsers(childClientId, null, testUsername, null,
        // null, null, 0, 1);
        System.out.println("findUsersResponse=" + findUsersResponse);
    }

}
