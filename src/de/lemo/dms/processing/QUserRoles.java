package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.resulttype.ResultList;
import de.lemo.dms.processing.resulttype.RoleObject;

@Path("userroles")
public class QUserRoles extends Question{

	@Override
	protected List<Parameter<?>> getParameterDescription() {
		
		List<Parameter<?>> parameters = new LinkedList<Parameter<?>>();        
        return parameters;
	}
	
	
	@GET
    public ResultList getUserRoles() {
	
		ResultList res = null;
		
		//Set up db-connection
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		ArrayList<RoleMining> roleMining = (ArrayList<RoleMining>) dbHandler.performQuery(EQueryType.HQL, "from RoleMining");
		
		ArrayList<RoleObject> roles = new ArrayList<RoleObject>();
		for(int i= 0; i < roleMining.size(); i++)
		{
			RoleObject ro = new RoleObject(roleMining.get(i).getId(), roleMining.get(i).getName());
			roles.add(ro);
		}
		
		res = new ResultList(roles);
		
		return res;
		
	}

}