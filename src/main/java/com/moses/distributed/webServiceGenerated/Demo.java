package com.moses.distributed.webServiceGenerated;

public class Demo {
	public static void main(String[] args) {
		ServerServiceImplService service = new ServerServiceImplService();
		ServerServiceImpl impl = service.getServerServiceImplPort();
		System.out.println(impl.sayHello("test name"));
	}
	
}
