package simulator;


import java.util.HashMap;
import java.util.Map;

/**
 * 
 * vertex in ATP env class 
 *
 */
public class Vertex implements Comparable<Vertex> {
	
	private int _number;
	private Map<String,Car> _cars;
	private Map<Vertex,Road> _neighbours;
	
	
	public Vertex(int number) {
		super();
		_cars = new HashMap<String,Car>(); 
		_neighbours = new HashMap<Vertex,Road>();
		_number = number;
	}


	public Vertex(int number, Map<String,Car> cars) {
		super();
		_neighbours = new HashMap<Vertex,Road>();
		_number = number;
		_cars = cars;
	}

	public Vertex(int number, Map<String,Car> cars,  Map<Vertex,Road> neighbours) {
		super();
		_neighbours = neighbours;
		_number = number;
		_cars = cars;
	}

	public int get_number() {
		return _number;
	}

	

	public void set_number(int number) {
		_number = number;
	}



	public Map<String,Car> get_cars() {
		return _cars;
	}



	public void set_cars(Map<String,Car> cars) {
		_cars = cars;
	}


	public Map<Vertex,Road> get_neighbours() {
		return _neighbours;
	}


	public void set_neighbours(Map<Vertex,Road> neighbours) {
		_neighbours = neighbours;
	}
	
	public void addNeighbour(Vertex v , Road e){
		if (!get_neighbours().containsKey(v)){
			get_neighbours().put(v,e);
		}
	}


	public void addCar(String carName, int carSpeed, double carCoff) {
		get_cars().put(carName , new Car(carName, carSpeed, carCoff));
		
	}
	
	public void addCar(Car car) {
		get_cars().put(car.get_name() , car);
	}
	


	@Override
	public String toString() {
		return "Vertex:\t_number=" + _number 
				+ carsToString();
	}


	public String carsToString() {
		String ret = ". ";
		//if (_cars.isEmpty()) ret = ret.concat("\n"); 
		for(String c:_cars.keySet()){
			ret = ret.concat("\n ");
			ret = ret.concat(_cars.get(c).toString());
			
		}
		
		return ret;
	}


	@Override
	public int compareTo(Vertex o) {
		if(get_number()>o.get_number()){
			return 1;			
		}
		if(get_number()<o.get_number()){
			return -1;			
		}
		return 0;
	}
		
}
