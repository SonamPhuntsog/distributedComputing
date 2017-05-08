package distributedcomputing;

import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import javax.swing.JTextArea;
import org.jblas.DoubleMatrix;

/*
UnitHub starts RegisterServer Thread

and when at least one volunteer has signed in
UnitHub can call a distributed mathon (eg. distributedAdd)
which will break up the problem into peaces where each peace is run as Unit in
seperate Callable Thread

private List<InetSocketAddress> register;
is what methods defined in IRegister is accessing
it contains (ip,port) of all the volunteers who has signed interface MyInterface extends Parent {

}
*/
public class UnitHub implements IRegister {

    private List<InetSocketAddress> register;
    private RegisterServer rs;
    private JTextArea textArea;
    UnitHub(int registerServerPort, JTextArea textArea){
      register = new ArrayList<>();
      rs = new RegisterServer(this, registerServerPort );
      this.textArea = textArea;
    }
    @Override
    public int getRegisterSize(){
      return register.size();
    }
    @Override
    public List<InetSocketAddress> getRegister(){
      return register;
    }
    @Override
    public void addToRegister(InetSocketAddress addr){
      if(!getRegister().contains(addr))
        getRegister().add(addr);
      textArea.setText(printRegister());
    }
    @Override
    public void removeFromRegister(InetSocketAddress addr){
      getRegister().remove(addr);
      textArea.setText(printRegister());
      
    }
    @Override
    public String printRegister(){
      return getRegisterSize()+" registered units:\n"+getRegister();
    }



    public DoubleMatrix distributedCalc(DoubleMatrix A, DoubleMatrix B, char c) throws Exception, NumberFormatException
{

	//char can be +, -, *

      /*
        use getRegisterSize() to find number of volunteers which is the
            maximum number of parts we can break the problem into
        use getRegister() to get a List of volunteer addresses
        todo - break the problem into maximum number possible given operand size
              but less than getRegisterSize()
              note: add each sub problem into l and executor.invokeAll(l); will return
                    list of Future containing answer to each sub problems
      */

    DoubleMatrix ans;
    ExecutorService executor = Executors.newCachedThreadPool();
    List<Unit> l = new ArrayList<>();

    int k = getRegisterSize();
    if( k == 0)
        throw new Exception();
    int count = 0;

    if( A.getColumns() != B.getColumns() || A.getRows() != B.getRows()){
    throw new NumberFormatException();
    }
    
    int p = A.getRows();
    int q = A.getColumns();
    
    
    //DoubleMatrix T1 = DoubleMatrix.zeros(1,1), T2 = DoubleMatrix.zeros(1,1);
    DoubleMatrix T1,T2;
	  for(int i = 0 ; i < p; i++)
	  {

		for(int j = 0; j < q; j++)
		{   T1 = DoubleMatrix.zeros(1,1);
                    T2 = DoubleMatrix.zeros(1,1);
                    double t1 = A.get(i,j);
                    double t2 = B.get(i,j);
                    T1.put(0,0,t1);
                    T2.put(0,0,t2);
                    
                    l.add(new Unit(T1, T2, c, getRegister().get(count%k)));
                    System.out.println(T1 + " : " + T2 + c);
                    count++;
		}
                
	}





      //l.add(new Unit(A, B, '+', getRegister().get(0) ));
      List <Future<DoubleMatrix> > results = executor.invokeAll(l);
      executor.shutdown();

	  double temp[] = new double[p*q];
          DoubleMatrix temp3;
	  for(int i = 0 ; i < p*q ; i++)
	  {
            temp3 = results.get(i).get();
            System.out.println(temp3);
            temp[i] = temp3.get(0,0);
	  }
          
          int v = 0;
    	  ans = DoubleMatrix.zeros(p,q);
          for(int i = 0; i < p; i++){
              for(int j = 0; j < q; j++){
                  ans.put(i,j,temp[v++]);
              }
          }


      /*
      for (Future<DoubleMatrix> result : results) {
         System.out.println(result.get());
      }
      */
      //ans = results.get(0).get();
      return ans;

    }


	public DoubleMatrix MatrixMul(DoubleMatrix A, DoubleMatrix B) throws Exception, NumberFormatException
	{

	DoubleMatrix ans;
      ExecutorService executor = Executors.newCachedThreadPool();
      List<Unit> l = new ArrayList<>();

	int k = getRegisterSize();
        if(k == 0)
        throw new Exception();
	  int count = 0;

	  if( A.getColumns()!= B.getRows())
	  {
	  throw new NumberFormatException();
	  }

	DoubleMatrix temp1, temp2;

	for(int i = 0; i < A.getRows(); i++)
	{
            temp1 = A.getRow(i);
            for(int j = 0; j < B.getColumns(); j++)
            {
                temp2 = B.getColumn(j);
                l.add(new Unit(temp1, temp2, 'M', getRegister().get(count%k)));
                count++;
            }
        }

	List <Future<DoubleMatrix> > results = executor.invokeAll(l);
      executor.shutdown();

	  double temp[] = new double[count];
	  DoubleMatrix temp3;

	  for(int i =0 ; i< count ; i++)
	  {
	  temp3 = results.get(i).get();
	  temp[i] = temp3.get(0,0);

	  }
          int v = 0;
	  ans = DoubleMatrix.zeros(A.getRows(),B.getColumns());
          for(int i = 0; i < A.getRows(); i++){
              for(int j = 0; j < B.getColumns(); j++){
                  ans.put(i,j,temp[v++]);
              }
          }
          
          return ans;
	}


/*
    public static void main(String[] args) throws Exception {
        UnitHub hub = new UnitHub();
        RegisterServer rs = new RegisterServer(hub, new InetSocketAddress( args[0], Integer.parseInt(args[1]) ) );

        DoubleMatrix A = DoubleMatrix.ones(3,3);
        DoubleMatrix B = DoubleMatrix.ones(3,3);

        Scanner sc = new Scanner(System.in);
        while(true){
        System.out.println(">>Enter when more than one volunteer signed in");
        sc.nextLine();
        if(hub.getRegisterSize() > 0){
          hub.printRegister();
          DoubleMatrix C = hub.MatrixMul(A, B);
          System.out.println("A ** B = " + C);
        }else{
          System.out.println("no volunteer");
        }
      }





    }
    */
}

/*
method 1
ExecutorService executor = Executors.newCachedThreadPool();
List<Unit> l = Arrays.asList(new Unit(...), new Unit(...), new Unit(...));
List <Future<Long>> results = executor.invokeAll(l);
executor.shutdown();
for (Future<Long> result : results) {
   System.out.println(result.get());
}


method 2
ExecutorService executor = Executors.newCachedThreadPool();
Future a = executor.submit( new Unit(...));
Future b = executor.submit( new Unit(...));
executor.shutdown();
System.out.println(a.get());
System.out.println(b.get());

*/
