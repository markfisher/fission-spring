/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.function.fission;

import java.util.Date;

import org.springframework.cloud.function.compiler.proxy.ByteCodeLoadingFunction;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * @author Mark Fisher
 */
@RestController
public class FissionController {

	private ByteCodeLoadingFunction<Flux<String>, Flux<String>> function;

	@PostMapping("/specialize")
	public void specialize() throws Exception {
		this.function = new ByteCodeLoadingFunction<>(new FileSystemResource("/userfunc/user"));
		this.function.afterPropertiesSet();
	}

	@PostMapping("/")
	public String invoke(@RequestBody String input) {
		return this.invokeFunction(input);
	}

	@GetMapping("/")
	public String invoke() {
		// for now pass the current date/time input for a GET
		return this.invokeFunction(new Date().toString());
	}

	private String invokeFunction(String input) {
		return this.function.apply(Flux.just(input)).blockFirst();
	}
}
