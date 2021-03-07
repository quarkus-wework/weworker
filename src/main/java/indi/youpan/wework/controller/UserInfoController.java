package indi.youpan.wework.controller;

import indi.youpan.wework.api.DepartmentApi;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("userInfo")
@Slf4j
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
public class UserInfoController {
  @Inject @RestClient private DepartmentApi departmentApi;
}
