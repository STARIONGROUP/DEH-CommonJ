package ViewModels.Dialogs.Rows;

import Enumerations.MappingDirection;
import Services.MappingEngineService.TestRules.Sphere;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.engineeringmodeldata.Requirement;

public class MappedRequirementSphereRowViewModel extends MappedElementRowViewModel<Requirement, Sphere>
{

	public MappedRequirementSphereRowViewModel(Requirement thing, Class<Requirement> clazz, Sphere dstElement,
			MappingDirection mappingDirection)
	{
		super(thing, clazz, dstElement, mappingDirection);
	}

	@Override
	public String GetDstElementRepresentation()
	{
		return String.format("%s-%s", this.GetDstElement().GetId(), this.GetDstElement().GetName());
	}

	@Override
	public String GetHubElementRepresentation()
	{
		return this.GetHubElement().getName();
	}
}
