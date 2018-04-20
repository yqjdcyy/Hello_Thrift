package com.yao.thrift;/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Generated code

import com.yao.thrift.shared.SharedStruct;
import com.yao.thrift.tutorial.Calculator;
import com.yao.thrift.tutorial.Operation;
import com.yao.thrift.tutorial.Work;
import com.yao.thrift.tutorial.InvalidOperation;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class JavaClient {
    public static void main(String[] args) {

        try {
            TTransport transport;
            if (false) {
                transport = new TSocket("localhost", 8888);
                transport.open();
            } else {
        /*
         * Similar to the server, you can use the parameters to setup client parameters or
         * use the default settings. On the client side, you will need a TrustStore which
         * contains the trusted certificate along with the public key.
         * For this nettyExample it's a self-signed cert.
         */
                TSSLTransportParameters params = new TSSLTransportParameters();
//                params.setTrustStore(JavaClient.class.getResource("").toString().replace("target/classes/", "src/main/java/").replaceAll("file:", "")+ "/keystore/truststore.jks", "nd.com.cn");
                params.setTrustStore("D:\\WorkSpace\\work4IDEA\\HelloWorld4Maven\\SpringMvc\\src\\main\\java\\com\\yao\\springmvc\\thrift\\keystore\\truststore.jks", "nd.com.cn");
        /*
         * Get a client transport instead of a server transport. The connection is opened on
         * invocation of the factory method, no need to specifically call open()
         */
                transport = TSSLTransportFactory.getClientSocket("localhost", 9999, 0, params);
            }

            TProtocol protocol = new TBinaryProtocol(transport);
            Calculator.Client client = new Calculator.Client(protocol);
            perform(client);
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(Calculator.Client client) throws TException {
        client.ping();
        System.out.println("ping()");

        int sum = client.add(1, 1);
        System.out.println("1+1=" + sum);

        Work work = new Work();

        work.op = Operation.DIVIDE;
        work.num1 = 1;
        work.num2 = 0;
        try {
            int quotient = client.calculate(1, work);
            System.out.println("Whoa we can divide by 0");
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + io.why);
        }

        work.op = Operation.SUBTRACT;
        work.num1 = 15;
        work.num2 = 10;
        try {
            int diff = client.calculate(1, work);
            System.out.println("15-10=" + diff);
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + io.why);
        }

        SharedStruct log = client.getStruct(1);
        System.out.println("Check log: " + log.value);
    }
}
