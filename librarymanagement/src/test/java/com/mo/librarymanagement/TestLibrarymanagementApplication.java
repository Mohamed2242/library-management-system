package com.mo.librarymanagement;

import org.springframework.boot.SpringApplication;

public class TestLibrarymanagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(LibrarymanagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
