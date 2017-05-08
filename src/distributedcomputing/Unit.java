package distributedcomputing;
import java.util.concurrent.*;
import java.rmi.*;
import java.net.*;
import org.jblas.DoubleMatrix;

/*
Each Unit solves one peace of the whole problem

in Unit(DoubleMatrix op1, DoubleMatrix op2, char op, InetSocketAddress addr)
op is the bynary operation to be performed and addr is the (ip,port) of rmi which
is going to actually compute the answer
*/
class Unit implements Callable<DoubleMatrix> {
    private DoubleMatrix op1;
    private DoubleMatrix op2;
    private char op;
    private InetSocketAddress addr;

        Unit(DoubleMatrix op1, DoubleMatrix op2, char op, InetSocketAddress addr) {
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
        this.addr = addr;
    }

    @Override
    public DoubleMatrix call() {

      DoubleMatrix ans = null;
      try{
        String url="rmi://"+addr.getHostName()+":"+addr.getPort()+"/UnitCompute";
        IUnitCompute iuc = (IUnitCompute) Naming.lookup(url);
        switch(op){
          case '+':
            ans = iuc.add(op1, op2);
            break;
          case '-':
            ans = iuc.sub(op1, op2);
            break;
          case '*':
            ans = iuc.mul(op1, op2);
            break;
          case 'M' :
            ans = iuc.mmul(op1, op2);
            break;
          default:
            throw new Exception("operator "+op+" not found");
        }
        System.out.println(op1 +" "+ op +" "+ op2 + " = " + ans);
      }
      catch(RemoteException re){
        System.out.println("Remote Exception :"+re.toString());
      }
      catch(Exception e){
        e.printStackTrace();
      }

      return ans;
    }
}
