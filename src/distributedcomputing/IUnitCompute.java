package distributedcomputing;
import java.rmi.*;
import org.jblas.DoubleMatrix;

/*
this interface is implemented by UnitCompute
todo - more operations needs to be added
*/
public interface IUnitCompute extends Remote{
  public DoubleMatrix add(DoubleMatrix A, DoubleMatrix B)throws RemoteException;
  public DoubleMatrix sub(DoubleMatrix A, DoubleMatrix B)throws RemoteException;
  public DoubleMatrix mul(DoubleMatrix A, DoubleMatrix B)throws RemoteException;
  public DoubleMatrix mmul(DoubleMatrix A, DoubleMatrix B) throws RemoteException;

}
