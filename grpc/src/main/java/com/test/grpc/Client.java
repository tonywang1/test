package com.test.grpc;

import com.test.tutorial.TestProto;
import com.test.tutorial.TestServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {

        Client client = new Client();
        try {
            String user = "大司马";
            //调用对应的方法
            client.test(user);
        } finally {
        }
    }

    public void test(String name) {
        TestProto.TestInput request = TestProto.TestInput.newBuilder().setKey(name).build();
        TestProto.TestOutput response;
        try {
            Channel channel =  ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build();
            TestServiceGrpc.TestServiceBlockingStub blockingStub =TestServiceGrpc.newBlockingStub(channel);
            //调用方法
            response = blockingStub.testFunction(request);
        } catch (StatusRuntimeException e) {
            return;
        }
        System.out.println( "Greeting: " + response.getKey());
    }


}
