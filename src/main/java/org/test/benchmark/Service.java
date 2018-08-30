package org.test.benchmark;

import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class Service {

	
	@RequestMapping(method = RequestMethod.GET, produces={"text/plain"})
	public String processInput(@RequestParam(name="number", required=false) Long number){
		
		long start = System.currentTimeMillis();
		HashMap<Long,Boolean> map = new HashMap<>();
		
		StringBuffer buffer = new StringBuffer("{");
		
		for (long testNum = 1; testNum < (number == null ? 100000 : number); testNum++){
			map.put(testNum, this.isPrime(testNum));
		}
		
		for(Entry<Long,Boolean> entry : map.entrySet()){
			buffer.append("'");
			buffer.append(entry.getKey());
			buffer.append("': ");
			buffer.append(entry.getValue());
			buffer.append(",");
		}
		
		buffer.replace(buffer.length() - 1, buffer.length(), "}");
		
		return "{'timeMs': " + (System.currentTimeMillis() - start) + ", 'primes': " + buffer + "}";
	}
	
	private boolean isPrime(long number){
		boolean isPrime = true;
		for (long num = 2; num < number; num++){
			if((number % num) == 0){
				isPrime = false;
				break;
			}
		}
		
		return isPrime;
	}
	
}
