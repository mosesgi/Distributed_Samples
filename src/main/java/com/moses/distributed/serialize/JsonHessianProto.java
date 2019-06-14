package com.moses.distributed.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class JsonHessianProto {
	public static void main(String[] args) throws IOException {
		useJacksonJson();
		useFastJson();
		
		useBaiduProtoBuf();
		useHessian();
	}
	
	private static Person init() {
		Person p = new Person();
		p.setName("Moses");
		p.setAge(28);
		return p;
	}
	
	private static void useJacksonJson() throws IOException{
		Person p = init();
		ObjectMapper mapper = new ObjectMapper();
		Long start = System.currentTimeMillis();
		byte[] writeBytes= null;
		for(int i=0; i<10000; i++) {
			writeBytes = mapper.writeValueAsBytes(p);
		}
		System.out.println("Json serializable: " + (System.currentTimeMillis() - start) + " ms. " + "total size: " + writeBytes.length);
		Person p1 = mapper.readValue(writeBytes, Person.class);
		System.out.println(p1);
		
	}
	
	private static void useFastJson() throws IOException {
        Person person=init();
        String text=null;
        Long start=System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            text=JSON.toJSONString(person);
        }
        System.out.println("fastjson序列化："+(System.currentTimeMillis()-start)+"ms : " +
                "总大小->"+text.getBytes().length);

        Person person1=JSON.parseObject(text,Person.class);
        System.out.println(person1);
    }
	
	private static void useBaiduProtoBuf() throws IOException{
		Person p = init();
		Codec<Person> personCodec = ProtobufProxy.create(Person.class, false);
		Long start = System.currentTimeMillis();
		byte[] bytes = null;
		for(int i = 0; i<10000; i++) {
			bytes = personCodec.encode(p);
		}
		System.out.println("protobuf serialize: " + (System.currentTimeMillis()-start) + " ms. " + " total size: " + bytes.length);
		Person p1 = personCodec.decode(bytes);
		System.out.println(p1);
	}
	
	private static void useHessian() throws IOException{
		Person p = init();
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		long start = System.currentTimeMillis();
		for(int i = 0; i< 10000; i++) {
			ho.writeObject(p);
			if(i == 0) {
				System.out.println(os.toByteArray().length);
			}
		}
		System.out.println("Hessian serialize: " + (System.currentTimeMillis() - start) + " ms.");
		HessianInput hi = new HessianInput(new ByteArrayInputStream(os.toByteArray()));
		Person p1 = (Person)hi.readObject();
		System.out.println(p1);
	}
}
