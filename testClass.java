
public class testClass extends testAbstract{

	int baseLevelVal = 1;
	
	testAbstract2 t = new testAbstract2();
	
	
	UnrelatedClass unrelated = new UnrelatedClass();
	
	testClass(int baseLevelVal)
	{
	
		this.baseLevelVal = baseLevelVal;
		
		
	}
	
	public String toString() { return "testClass"; }

	@Override
	public int absMethod() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
