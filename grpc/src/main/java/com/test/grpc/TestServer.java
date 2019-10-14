package com.test.grpc;

import com.test.tutorial.TestProto;
import com.test.tutorial.TestServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class TestServer {

    Server server;

    public static void main(String[] args) throws IOException {

        final TestServer testServer = new TestServer();
        //启动server
        testServer.start();
        //block Server防止关闭
//        server.blockUntilShutdown();

    }

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        //这个部分启动server
        server = ServerBuilder.forPort(port)
                .addService(new TestImpl())
                .build()
                .start();
//        server.blockUntilShutdown();

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class TestImpl extends TestServiceGrpc.TestServiceImplBase {

        //基础TestServiceImplBase这个基础类实现里面的对应方法
        @Override
        public void testFunction(TestProto.TestInput req, StreamObserver<TestProto.TestOutput> responseObserver) {
            TestProto.TestOutput reply = TestProto.TestOutput.newBuilder().setKey(" 说好的： "+req.getKey()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

}
