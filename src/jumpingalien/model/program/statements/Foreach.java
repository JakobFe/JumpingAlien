package jumpingalien.model.program.statements;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jumpingalien.model.game.World;
import jumpingalien.model.program.expressions.Constant;
import jumpingalien.model.program.expressions.Expression;
import jumpingalien.model.program.programs.Program;
import jumpingalien.model.program.types.ObjectOfWorld;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class Foreach extends SingleStatement {

	public Foreach(String variableName,
			Kind variableKind,
			Expression where,
			Expression sort,
			SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.variableName = variableName;
		this.variableKind = variableKind;
		this.restriction = where;
		this.sortingExpression = sort;
		this.sortDirection = sortDirection;
		this.body = body;		
	}
	
	public String getVariableName() {
		return variableName;
	}

	private final String variableName;

	public Kind getVariableKind() {
		return variableKind;
	}
	
	private final Kind variableKind;
	
	private void assign(ObjectOfWorld theObject) {
		assert (getProgram() != null);
		Assignment assignment = 
				new Assignment(getVariableName(), 
								new ObjectOfWorld(),
								new Constant<ObjectOfWorld>(getSourceLocation(),theObject),
								getSourceLocation());
		assignment.setProgram(getProgram());
		assignment.executeSingleStatement();
	}

	public Expression getRestriction() {
		return restriction;
	}
	
	private boolean checkRestriction(ObjectOfWorld theObject){
		assert (getProgram() != null);
		if(getRestriction() == null)
			return true;
		assign(theObject);
		return (Boolean)getRestriction().outcome();
	}
	
	private final Expression restriction;

	public Expression getSortingExpression() {
		return sortingExpression;
	}
	
	private double getSortValue(ObjectOfWorld theObject){
		assert (getProgram() != null);
		if(getSortingExpression() == null)
			return 0;
		assign(theObject);
		return (Double)getSortingExpression().outcome();
	}
	
	private final Expression sortingExpression;
	
	private final SortDirection sortDirection;

	public SortDirection getSortDirection() {
		return sortDirection;
	}
	
	public Statement getBody() {
		return body;
	}

	private final Statement body;

	@Override
	public void setProgram(Program program){
		super.setProgram(program);
		getBody().setProgram(program);
		if(getRestriction() != null)
			getRestriction().setProgram(program);
		if(getSortingExpression() != null)
			getSortingExpression().setProgram(program);
	}
	
	public List<ObjectOfWorld> getVariables() {
		return variables;
	}
	
	public ObjectOfWorld getVariableAt(int index){
		return variables.get(index);
	}

	// moet nog uitgebreid getests worden!!!
	public void setVariables() {
		assert(getProgram() != null);
		assert(getProgram().getGameObject() != null);
		assert(getProgram().getGameObject().getWorld() != null);
		
		HashSet<ObjectOfWorld> set = convertKind();
		Stream<ObjectOfWorld> filteredStream = set.stream().filter(s -> checkRestriction(s));		
		Stream<ObjectOfWorld> sortedStream = filteredStream;
//		Comparator<ObjectOfWorld> c = null;
		if(getSortDirection() == SortDirection.ASCENDING){
			sortedStream = filteredStream.sorted((o1,o2) -> 
							Double.compare(getSortValue(o1), getSortValue(o2)));
			
//			c = new Comparator<ObjectOfWorld>() {
//
//				@Override
//				public int compare(ObjectOfWorld o1, ObjectOfWorld o2) {
//					return Double.compare(getSortValue(o1), getSortValue(o2));
//				}
//	
//			};
		}
		else if(getSortDirection() == SortDirection.DESCENDING){
			sortedStream = filteredStream.sorted((o1,o2) -> 
							Double.compare(getSortValue(o2), getSortValue(o1)));
//			c = new Comparator<ObjectOfWorld>() {
//
//				@Override
//				public int compare(ObjectOfWorld o1, ObjectOfWorld o2) {
//					return Double.compare(getSortValue(o2), getSortValue(o1));
//				}
//	
//			};
		}
//		sortedStream = filteredStream.sorted(c);
		this.variables = sortedStream.collect(Collectors.toList());
	}

	private HashSet<ObjectOfWorld> convertKind() {
		World world = getProgram().getGameObject().getWorld();
		HashSet<ObjectOfWorld> set = new HashSet<ObjectOfWorld>();
		if(getVariableKind() == Kind.MAZUB){
			set.add(world.getMazub());
		}
		else if(getVariableKind() == Kind.BUZAM){
			set.add(world.getBuzam());
		}
		else if(getVariableKind() == Kind.SLIME){
			set.addAll(world.getAllSlimes());
		}
		else if(getVariableKind() == Kind.SHARK){
			set.addAll(world.getAllSharks());
		}
		else if(getVariableKind() == Kind.PLANT){
			set.addAll(world.getAllPlants());
		}
		else if(getVariableKind() == Kind.TERRAIN){
			set.addAll(world.getAllTiles());
		}
		else if(getVariableKind() == Kind.ANY){
			set.addAll(world.getAllGameObjects());
			set.addAll(world.getAllTiles());
		}
		return set;
	}

	private List<ObjectOfWorld> variables;
	
	@Override
	public void executeSingleStatement() {
		if(getProgram() != null){
			setVariables();
		}
	}

	@Override
	public StatementIterator<Statement> iterator() {
		return new StatementIterator<Statement>(){
			
			@Override
			public boolean hasNext() {
				return (!subIteratorsInitialized || 
						getIndex()<2);
			}
			
			@Override
			public Statement next() throws NoSuchElementException{
				if(!hasNext())
					throw new NoSuchElementException();
				if(!subIteratorsInitialized)
					initialiseSubIterators();
				if(getIndex() == 0){
					setIndex(1);
					executeSingleStatement();
					return Foreach.this;
				}
				else if(getIndex() == 1){
					if(!bodyStarted){
						assign(getVariableAt(getVariableIndex()));
						bodyStarted = true;
					}
					if(bodyIterator.hasNext())
						return bodyIterator.next();
					else if(getVariableIndex() < (getVariables().size()-1)){
						bodyIterator.restart();
						bodyStarted = false;
						incrementVariableIndex();
						return this.next();
					}
					else{
						setIndex(2);
						assign(null);
					}
					
				}
				return null;
			}
			
			@Override
			public void restart() {
				setIndex(0);
				bodyIterator.restart();
				bodyStarted = false;
				setVariableIndex(0);
				assign(null);
			}
			
			@Override
			public int getIndex() {
				return this.index;
			}
			
			@Override
			public void setIndex(int index) {
				this.index = index;
			}
			
			private int index = 0;
			
			private void initialiseSubIterators(){
				subIteratorsInitialized = true;
				bodyIterator = getBody().iterator();

			}
			
			private StatementIterator<Statement> bodyIterator;
			
			private boolean subIteratorsInitialized = false;
			
			public int getVariableIndex() {
				return variableIndex;
			}
			
			public void incrementVariableIndex(){
				setVariableIndex(getVariableIndex() + 1);
			}
			
			public void setVariableIndex(int variableIndex) {
				this.variableIndex = variableIndex;
			}

			private int variableIndex = 0;
			
			private boolean bodyStarted = false;
		};
	}

	
}
