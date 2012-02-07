package agents;

import java.util.LinkedHashMap;
import java.util.Vector;
import simulator.Car;
import simulator.Environment;
import simulator.MoveAction;
import simulator.Road;
import simulator.SwitchCarAction;
import simulator.Vertex;
import simulator.Interfaces.Action;

public class PropabilityAgent extends Agent {

	protected Vector<Action> _actions;
	Vector<BeliefStateNode> _beliefStates;
	
	public PropabilityAgent(String name, Vertex initPosition,
			Vertex goalPosition, Car car) {
		super(name, initPosition, goalPosition, car);
		// TODO Auto-generated constructor stub
	}

	/**
	 * this the general search method, it creates a problem and run the general search algorithm
	 * on it, then translate the resulted chain of DecisionNodes to Actions
	 */
	@Override
	public void search(Environment env, Vertex initPos,Vertex goalPosition,Car initCar ){		
		 _beliefStates = buildStates(env);
		 //clearStates(env);
		 setGoals(env);
		 createLinks(env);
		 _actions = VALUE_ITERATION(_beliefStates,0.001,env);
		 System.out.println("finished searching!");
		 
	}
	
	public void search_again(Environment env, Vertex initPos,Vertex goalPosition,Car initCar ){		
		 _actions = VALUE_ITERATION(_beliefStates,0.001,env);
		 System.out.println("finished searching again!");
	}

	private void setGoals(Environment env) {
		for(BeliefStateNode bf : _beliefStates){
			if(bf._srcVertex.equals(this._goalPosition)){
				bf._utility=0.0;
			}
		}
	}

	private void clearStates(Environment env) {
		Vector <BeliefStateNode> clearVec = new Vector<BeliefStateNode>();
		for(BeliefStateNode bf : _beliefStates){
			if (bf.isLegal(env)){
				clearVec.add(bf);
			}
		}
		_beliefStates=clearVec;
	}

	private void createLinks(Environment env) {
		int i = 0;
		for(BeliefStateNode bf : _beliefStates)
		{
			i++;
			bf.calcChildren(_beliefStates, env);
			//System.out.println(bf);
			//System.out.println("finished calculating "+ bf._srcVertex );
		}
		//System.out.println("****************** "+i+" *******************");
	}

	

	private Vector<Action> VALUE_ITERATION(Vector<BeliefStateNode> beliefStates, double epsilon,Environment env) {
		
		int maxIterations = 1000;
		int i = 0;
		double gamma = 1.0;
		double minDelta = epsilon * (1 - gamma) / gamma;
		double delta;
		
		do 
		{
			delta = 0;
			for (BeliefStateNode bsn : beliefStates)
			{
				if(bsn._utility==0.0){
					continue;					
				}
				double Uvalue = bsn._utility;
				double UtagValue = Uvalue;
				Vector<Action> acVec = new Vector<Action>();
				double maximizedActionUtil = calcMaximumActionUtil(bsn,acVec,env,gamma);				
				bsn._action = acVec.get(0); 
				UtagValue = maximizedActionUtil;
				bsn._utility = UtagValue; 
				double newDelta = Math.abs(UtagValue-Uvalue);
				if (newDelta>delta){
					delta = newDelta; 
				}
			}
		}while ((delta > minDelta) && ((i++)<maxIterations));
		
		BeliefStateNode start_bf = null;
		
		LinkedHashMap<Road,Double> predRoadsExpt = new LinkedHashMap<Road,Double>() ;
		for(Road r : _vertex.get_neighbours().values())
		{
			if ((r.get_floodedProb()<1) && (r.get_floodedProb()>0))
				predRoadsExpt.put(r, r.is_flooded()? 1.0:0.0);
		}
		
		for(BeliefStateNode bf : _beliefStates){
			if(!bf._srcVertex.equals(_vertex)){
				continue;
			}
			
			if(!bf._agentCar.equals(this._car)){
				continue;
			}
			
			if (env.getCarOfVertex(bf._otherCarVertex.get_number(),bf._otherCar.get_name())==null)
			{
				continue;
			}
			boolean flag = true;
			
			for(Road r : bf._probRoads.keySet()){
				for(Road pr : predRoadsExpt.keySet()){
					if (((r.get_from().equals(pr.get_from()))&&(r.get_to().equals(pr.get_to()))||
							(r.get_from().equals(pr.get_to()))&&(r.get_to().equals(pr.get_from())))){
						if (predRoadsExpt.get(r)<1 && predRoadsExpt.get(r)>0) 
							flag = false;
					}else{
						if (!(predRoadsExpt.get(r)<1 && predRoadsExpt.get(r)>0))
							flag = false;
					}
					if (flag == false) continue;
				}
				if (flag == false) continue; 
			}
			
			start_bf = bf;
			break;
		}
		
		//find action list
		Vector<Action> actions = new Vector<Action>();
		calcActions(start_bf,actions); 
		System.out.println("finished calculating actions!");
		return actions;
	}


	
	private void calcActions(BeliefStateNode startBf, Vector<Action> vec) {
		
		if (startBf._utility == 0.0) return;
		vec.add(startBf._action);
		BeliefStateNode child = findMaxChild(startBf._childeren,startBf._action);		
		calcActions(child,vec);
	}

	private BeliefStateNode findMaxChild(
		LinkedHashMap<Action, Vector<BeliefStateNode>> childeren,Action ac) 
	{			
	
		double maxVal = Double.NEGATIVE_INFINITY;
		BeliefStateNode maxChild = null;
		
			
		for (BeliefStateNode ch: childeren.get(ac)){
			if ((ch._utility+calcReward(ch,ac))>=maxVal){
				maxChild = ch;
				maxVal = ch._utility+calcReward(ch,ac); 
			}
		}
			
		return maxChild;
	}
	
	
	private Double calcMaximumActionUtil(BeliefStateNode bsn, Vector<Action> acVec, Environment env, double gamma) {
		Action ac = bsn._action;
		double finalreward = bsn._reward; 
		double max = bsn._utility;
		
		for(Action a: bsn._childeren.keySet()){
			double tmp  = 0;
			Vector<BeliefStateNode> childBSNs = bsn._childeren.get(a);
			for(BeliefStateNode childBSN : childBSNs){
					if (a instanceof MoveAction){
					Road r1 = bsn._srcVertex.get_neighbours().get(childBSN._srcVertex);
					Road r2 = childBSN._srcVertex.get_neighbours().get(bsn._srcVertex);
					if(bsn._probRoads.containsKey(r1)){
						if((bsn._probRoads.get(r1) > 0) && (bsn._probRoads.get(r1)<1)){
							tmp += childBSN._probRoads.get(r1).doubleValue()*childBSN._utility;
						}
						else {
							tmp += childBSN._utility;
						}
							
					}
					else if(bsn._probRoads.containsKey(r2)){
						if((bsn._probRoads.get(r2) > 0) && (bsn._probRoads.get(r2)<1)){
							tmp += (childBSN._probRoads.get(r2).doubleValue())*childBSN._utility; 
						}
						else {
							tmp += childBSN._utility;
						}
					}else{
						tmp += childBSN._utility;
					}
				}else if (a instanceof SwitchCarAction){ 
					tmp = childBSN._utility;
				}			
			}
			
			Double reward = calcReward(bsn,a);
			tmp = (reward+(gamma*tmp));
			
			if (tmp>max){				
				ac=a;
				finalreward = reward;
				max=tmp;
			}	
		}
		acVec.add(ac);
		bsn._reward = finalreward;
	//	System.out.println("max: "+max+", src vertex: "+bsn._srcVertex.get_number()+", Action: "+ac);
		return max;
	}

	private Double calcReward(BeliefStateNode bsn,Action a) {			
		return a.getReward(bsn._srcVertex,bsn);
	}

	private Vector<BeliefStateNode> buildStates(
			Environment env) {
		 
		Vector<BeliefStateNode> beliefNodes = new Vector<BeliefStateNode>();
		for(Vertex vs: env.get_vertexes().values()){
			for(Vertex vc: env.get_vertexes().values()){
				for(int j = 0; j<env.get_cars().size();j++){
						BeliefStateNode bn = new BeliefStateNode(vs, env.get_cars().get(j), 
																  env.get_cars().get(1-j),vc);
						Vector<Road> frv = new Vector<Road>();
						for(int i = 0; i<env.get_edges().size();i++){
							Road r =  env.get_edges().get(i);
							if (r.get_floodedProb()>0){
								frv.add(r);
							}
							i++;
						}
						Vector<LinkedHashMap<Road,Double>> permVer = generateProbRoads(frv);
						for(LinkedHashMap<Road,Double> d : permVer){
							beliefNodes.add(new BeliefStateNode(bn,d));
						}
						if (permVer.isEmpty()){
							beliefNodes.add(bn);
						}
				}
			}
		}
		
		System.out.print(beliefNodes.size());
		return beliefNodes;
	}


	private Vector<LinkedHashMap<Road,Double>> generateProbRoads(Vector<Road> frv) {
		
		Vector<LinkedHashMap<Road,Double>> ret = new Vector<LinkedHashMap<Road,Double>>(); 
		Vector<Vector<Double>> probs =  generateProbs(frv);
		for (Vector<Double> v: probs ){
			LinkedHashMap<Road,Double> dict = new LinkedHashMap<Road,Double>();
			int i = 0;
			for(Road r: frv){
				 dict.put(r, v.get(i));
				 i++;
			}
			ret.add(dict);
		}
		return ret;
	}
	

	private Vector<Vector<Double>> generateProbs(Vector<Road> frv) {
		
		Vector<Vector<Double>> ret = new Vector<Vector<Double>>();
		if (frv.size()==0){
			return ret;
		}
		
		if (frv.size()==1){
			
			Vector<Double> v1 = new Vector<Double>();
			v1.add(0.0);
			ret.add(v1);
			
			Vector<Double> v2 = new Vector<Double>();
			v2.add(1.0);
			ret.add(v2);
			
			Vector<Double> v3 = new Vector<Double>();
			v3.add(frv.get(0).get_floodedProb());
			ret.add(v3);
			
			return ret;
		}
		
		Vector<Road> tfrv = new Vector<Road>(frv); 
		Road r = tfrv.remove(0);
		Vector<Vector<Double>> tret = generateProbs(tfrv);
		
		for(Vector<Double> v : tret){
			Vector<Double> v1 = new Vector<Double>(v);
			v1.add(0,0.0);
			ret.add(v1);
			
			Vector<Double> v2 = new Vector<Double>(v);
			v2.add(0,1.0);
			ret.add(v2);
			
			Vector<Double> v3 = new Vector<Double>(v);
			v3.add(0,r.get_floodedProb());
			ret.add(v3);
		}
	
		return ret;
	}
	
	

	/**
	 * pop the first action From the list of periodic actions calculated by the search algorithm 
	 * and push it to the "next action to perform queue" 
	 */
	@Override
	public void chooseBestAction(Environment env) {
		if(!_actions.isEmpty()){
			Action action = _actions.remove(0);  //get next predicted action
			get_actions().offer(action);		//offer it to be the next performed action
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
