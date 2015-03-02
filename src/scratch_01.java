
public class scratch_01 {

	public static void main(String[] args) {
		
		Search search = new Search<Integer>(new Integer(10));
		
		System.out.println(search.getClasse());

	}
	

}

class Search<T> {
	
		private T value;

		public Search(T value){
			this.value = value;
						
		}
		
		public String getClasse(){
			return value.getClass().getName();
		}
		
		public T getValue(){
		
			return null;
			
		}
}
