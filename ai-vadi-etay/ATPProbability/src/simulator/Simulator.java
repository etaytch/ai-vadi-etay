package simulator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import simulator.Interfaces.Action;
import tools.ATPLogger;
import agents.Agent;



public class Simulator {
 
	private Map<Agent,Chart> _finishedAgents;
	private Environment _env;
	
	public Simulator(Map<Integer,Vertex> vertexes, Map<Agent, Chart> agents, Vector<Road> edges) {
		super();
		_env = new Environment(vertexes,agents,edges);
		_finishedAgents = new LinkedHashMap<Agent,Chart>();
	}
		
	public Simulator(Map<Integer,Vertex> vertexes, Vector<Road> edges ) {
		super();
		_env = new Environment(vertexes,edges);
		_finishedAgents = new LinkedHashMap<Agent,Chart>();
	}
	
	public Simulator(Environment env) {
		super();
		_env = env;
		_finishedAgents = new LinkedHashMap<Agent,Chart>();
	}
	
	public Simulator() {
		super();
		_env = new Environment();
		_finishedAgents = new LinkedHashMap<Agent,Chart>();
	}
	
	public void startSimulation() {
		int round=1;
		ATPLogger.log("Starting Simulation:");
		
		for (Agent agent: _env.get_agents().keySet()){
			ATPLogger.log("Agent: "+agent.get_name()+", start node: "+agent.get_initPosition().get_number()+", goal node: "+agent.get_goalPosition().get_number());
			agent.search(_env,agent.get_vertex(),agent.get_goalPosition(),agent.get_car());
		}
				
		while (!finished()){
			ATPLogger.log("\n<<<<<<<< Start of Round #"+(round++)+" >>>>>>>>");
			for (Agent agent: _env.get_agents().keySet()){
				ATPLogger.log("\n"+agent.get_name()+"'s turn:");
				agent.chooseBestAction(_env);
				Action a = agent.getAction();
				if(a!=null)	// currently null when SpeedNut gets 3 time to the same vertex
					a.performAction(this);
				ATPLogger.log(_env.get_agents().get(agent).toString());
				if (a==null){
					agent.search_again(_env,agent.get_vertex(),agent.get_goalPosition(),agent.get_car()); 
				}
			}			
		}
		ATPLogger.log("\nFinal Stats:");
		for (Agent agent: get_finishedAgents().keySet()){
			Chart c = get_finishedAgents().get(agent);
			c.set_totalTime((double)Math.round(c.get_totalTime() * 100) / 100);
			ATPLogger.log("\nAgent: "+agent.get_name()+": "+c);
			ATPLogger.log("For f="+Defs.F1_LEVEL+":       P = f * S + T =    "+Defs.F1_LEVEL+" * "+c._totalTime+" + "+c._expend_steps+" =     "+(Defs.F1_LEVEL*c._totalTime + c._expend_steps));
			ATPLogger.log("For f="+Defs.F100_LEVEL+":     P = f * S + T =    "+Defs.F100_LEVEL+" * "+c._totalTime+" + "+c._expend_steps+" =     "+(Defs.F100_LEVEL*c._totalTime + c._expend_steps));
			ATPLogger.log("For f="+Defs.F10000_LEVEL+":   P = f * S + T =    "+Defs.F10000_LEVEL+" * "+c._totalTime+" + "+c._expend_steps+" =     "+(Defs.F10000_LEVEL*c._totalTime + c._expend_steps));
		}
	}
	
	public Environment get_env() {
		return _env;
	}

	public void set_env(Environment env) {
		_env = env;
	}

	public Map<Agent, Chart> get_finishedAgents() {
		return _finishedAgents;
	}
	
	public void get_finishedAgents(Map<Agent, Chart> f_agents) {
		_finishedAgents = f_agents;
	}


	
	
	@Override
	public String toString() {
		return _env.toString();
	}
	
	
	public void updateAgentScore(Agent agent,Vertex old_vertex, Vertex new_vertex){
		double turnTime = 0;
		Chart chart  = _env.get_agents().get(agent);
		
		//the agent succeed and made the move
		chart.incrementAction();

		if (!old_vertex.get_neighbours().containsKey(new_vertex)){
			ATPLogger.log("agent tried to move from vertex to ileagal vertex!");
		}
		
		//Calculating turnTime in flooded road
		else if(old_vertex.get_neighbours().get(agent.get_vertex()).is_flooded()){
				turnTime +=	(old_vertex.get_neighbours().get(agent.get_vertex()).get_weight())/
							(agent.get_car().get_speed()*agent.get_car().get_coff());
		}
		
		//Calculating turnTime in clear road
		else{
			turnTime +=	(double)(old_vertex.get_neighbours().get(agent.get_vertex()).get_weight())/
						(agent.get_car().get_speed());
		}	
		ATPLogger.log("Agent "+agent.get_name()+" spent "+turnTime+" time moving to vertex #"+new_vertex.get_number());
		chart.addTime(turnTime);	
	}
	
	public void agentSwitchCar(Agent agent,Car old_car,String new_car_name)
	{
		Car new_car = agent.get_vertex().get_cars().get(new_car_name);
				
		if (new_car!=null){
			if(new_car.equals(old_car)){
				return;			
			}

			//remove the new car from the vertex
			agent.get_vertex().get_cars().remove(new_car_name);
			//give the new car to  the agent
			agent.set_car(new_car);
			
			//add the old car to the vertex car list
			agent.get_vertex().get_cars().put(old_car.get_name(),old_car);			
			ATPLogger.log("agent "+agent.get_name()+" switched car to: coff"+
								agent.get_car().get_coff()+", speed "+agent.get_car().get_speed()+".");
			Chart chart  = _env.get_agents().get(agent);
			chart.addTime(Defs.TSWITCH);
			chart.incrementAction();
		}
		else{
			ATPLogger.log("agent "+agent.get_name()+" failed to switche cars!\n");
		}
		//updae the agent's chart with "switch cars time"
		_env.get_agents().get(agent).addTime(Defs.TSWITCH);
		ATPLogger.log("Agent "+agent.get_name()+" spent "+Defs.TSWITCH+" time switching cars.");
	}
	
	public boolean moveAgent(Agent agent, Vertex new_vertex)
	{	
		Vertex old_vertex = agent.get_vertex();
		//check if destination vertex is a neighbor of agent vertex
		if (!agent.get_vertex().get_neighbours().containsKey(new_vertex)){
			updateAgentScore(agent,old_vertex,new_vertex);
			
			ATPLogger.log("Agent tried to move from vertex "+old_vertex.get_number()
								+" to not-neighbour vertex "+new_vertex.get_number()+"!");
			return false;
		}
		
		//check if the road is flooded and agent have a amphibious  car
		if (agent.get_vertex().get_neighbours().get(new_vertex).is_flooded() && 
				agent.get_car().get_coff()<=0){
			
			updateAgentScore(agent,old_vertex,new_vertex);
			
			ATPLogger.log("Agent tried to move on flooded road, from vertex "+
								old_vertex.get_number()+" to vertex "+new_vertex.get_number()+".");
			return false;
		}
		
		//make the move
		ATPLogger.log("Agent moved from vertex "+
				old_vertex.get_number()+" to vertex "+new_vertex.get_number()+".");
		agent.set_vertex(new_vertex);
		updateAgentScore(agent,old_vertex,new_vertex);
		return true;
	}


	public boolean finished() {
		boolean answer = true;
		Vector<Agent> toBeRemoved = new Vector<Agent>(); 
		if(_env.get_agents().size()==0){
			return true;
		}		
		for (Agent agent: _env.get_agents().keySet()){
			if(agent.get_state("stuck")!=null){
				get_finishedAgents().put(agent, _env.get_agents().get(agent));
				_env.get_agents().get(agent).set_totalTime(-999);
				//get_agents().remove(agent);
				toBeRemoved.add(agent);
				ATPLogger.log("Agent "+agent.get_name()+" is stuck! "+agent.get_state("stuck"));
			}
			else if (!agent.get_vertex().equals(agent.get_goalPosition())){
				answer = false;
			}	
			else{
				get_finishedAgents().put(agent, _env.get_agents().get(agent));
				//get_agents().remove(agent);
				toBeRemoved.add(agent);
				ATPLogger.log("Agent "+agent.get_name()+" Finished!");
				//ATPLogger.log("get_agents().keySet(): "+get_agents().keySet());
			}			
		}
		for(Agent agent:toBeRemoved){
			_env.get_agents().get(agent).set_expend_steps(agent.get_steps());
			_env.get_agents().remove(agent);			
		}
		if(answer){
			ATPLogger.log("All agents Finished!");
		}
		return answer;
	}
}
