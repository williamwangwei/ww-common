import org.junit.Test;

public class Test01 {

	
	@Test
	public void tt(){
		System.out.println("maxMemory============="+ Runtime.getRuntime().maxMemory());
		System.out.println("freeMemory============"+ Runtime.getRuntime().freeMemory());
		System.out.println("totalMemory==========="+Runtime.getRuntime().totalMemory());
	}
}
