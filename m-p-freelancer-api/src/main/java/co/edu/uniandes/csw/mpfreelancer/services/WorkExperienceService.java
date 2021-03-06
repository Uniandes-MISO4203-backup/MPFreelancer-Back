/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mpfreelancer.services;

import co.edu.uniandes.csw.auth.provider.StatusCreated;
import co.edu.uniandes.csw.mpfreelancer.api.IWorkExperienceLogic;
import co.edu.uniandes.csw.mpfreelancer.converters.WorkExperienceConverter;
import co.edu.uniandes.csw.mpfreelancer.dtos.WorkExperienceDTO;
import co.edu.uniandes.csw.mpfreelancer.entities.WorkExperienceEntity;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mf.calderon
 */

@Path("/workexperiences")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkExperienceService {

    @Inject private IWorkExperienceLogic workExperienceLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;

    @GET
    public List<WorkExperienceDTO> getExperience() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", workExperienceLogic.countExperience());
            return WorkExperienceConverter.listEntity2DTO(workExperienceLogic.getExperience(page, maxRecords));
        }
        return WorkExperienceConverter.listEntity2DTO(workExperienceLogic.getExperience());
    }

    @GET
    @Path("{id: \\d+}")
    public WorkExperienceDTO getExperience(@PathParam("id") Long id) {
        return WorkExperienceConverter.fullEntity2DTO(workExperienceLogic.getExperience(id));
    }

    @POST
    @StatusCreated
    public WorkExperienceDTO createExperience(WorkExperienceDTO dto) {
        WorkExperienceEntity entity = WorkExperienceConverter.fullDTO2Entity(dto);
        return WorkExperienceConverter.fullEntity2DTO(workExperienceLogic.createExperience(entity));
    }

    @PUT
    @Path("{id: \\d+}")
    public WorkExperienceDTO updateExperience(@PathParam("id") Long id, WorkExperienceDTO dto) {
        WorkExperienceEntity entity = WorkExperienceConverter.fullDTO2Entity(dto);
        entity.setId(id);
        return WorkExperienceConverter.fullEntity2DTO(workExperienceLogic.updateExperience(entity));
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteExperience(@PathParam("id") Long id) {
        workExperienceLogic.deleteExperience(id);
    }
    
    @GET
    @Path("{freelancerId: \\d+}/experienceFreelancer")
    public List<WorkExperienceDTO> EducationsFreelancer(@PathParam("freelancerId") Long freelancerId) {

        if (freelancerId != null) {
            return WorkExperienceConverter.listEntity2DTO(workExperienceLogic.getByFreelancer(freelancerId));
        } else {
            if (page != null && maxRecords != null) {
                this.response.setIntHeader("X-Total-Count", workExperienceLogic.countExperience());
                return WorkExperienceConverter.listEntity2DTO(workExperienceLogic.getExperience(page, maxRecords));
            }
            return WorkExperienceConverter.listEntity2DTO(workExperienceLogic.getExperience());
        }
    }

}