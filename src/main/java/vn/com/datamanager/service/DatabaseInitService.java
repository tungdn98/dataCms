package vn.com.datamanager.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.com.datamanager.config.ApplicationProperties;
import vn.com.datamanager.config.Constants;
import vn.com.datamanager.domain.EmpGroup;
import vn.com.datamanager.domain.Employee;
import vn.com.datamanager.domain.RoleGroup;
import vn.com.datamanager.domain.Roles;
import vn.com.datamanager.repository.EmpGroupRepository;
import vn.com.datamanager.repository.EmployeeRepository;
import vn.com.datamanager.repository.RoleGroupRepository;
import vn.com.datamanager.repository.RolesRepository;

/**
 * Khởi tạo 1 số dữ liệu cơ bản
 */
@Service
public class DatabaseInitService {

    private final Logger logger = LoggerFactory.getLogger(DatabaseInitService.class);

    private final EmpGroupRepository empGroupRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleGroupRepository roleGroupRepository;
    private final RolesRepository roleRepository;
    private final ApplicationProperties applicationProperties;

    public DatabaseInitService(
        EmpGroupRepository empGroupRepository,
        EmployeeRepository employeeRepository,
        RoleGroupRepository roleGroupRepository,
        RolesRepository roleRepository,
        ApplicationProperties applicationProperties
    ) {
        this.empGroupRepository = empGroupRepository;
        this.employeeRepository = employeeRepository;
        this.roleGroupRepository = roleGroupRepository;
        this.roleRepository = roleRepository;
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        try {
            if (applicationProperties.isInitSampleData()) {
                logger.info("[START] DATABASE SERVICE CREATE SAMPLE DATA");

                long totalEmpGroup = empGroupRepository.count();
                long totalEmployee = employeeRepository.count();
                long totalRoleGroup = roleGroupRepository.count();
                long totalRole = roleRepository.count();
                EmpGroup empGroup = new EmpGroup();
                if (totalEmpGroup == 0 && totalEmployee == 0) {
                    // Init database sample

                    empGroup.setGroupName("Developer");
                    empGroup.setCreatedBy(Constants.SYSTEM);
                    empGroup.setCreatedDate(Instant.now());

                    empGroup = empGroupRepository.save(empGroup);

                    Employee employee = new Employee();
                    employee.setEmployeeCode("11281321");
                    employee.setEmployeeName("Dao Ngoc Tung");
                    employee.setUsername("tungdn");
                    employee.setEmpGroup(empGroup);
                    employee.setActive(1);

                    employee = employeeRepository.save(employee);
                }

                if (totalRoleGroup == 0 && totalRole == 0) {
                    // Cau hinh
                    RoleGroup roleGroup = new RoleGroup();
                    roleGroup.setGroupName("Quản lý cấu hình hệ thống");
                    roleGroup = roleGroupRepository.save(roleGroup);

                    if (roleGroup.getId() > 0) {
                        Roles role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("configlog/getallconfiglogs");
                        role.setResourceDesc("Xem danh sách cấu hình hệ thống");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("ROLE_USER");
                        role.setResourceDesc("ROLE USER MẶC ĐỊNH");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("config/createconfig");
                        role.setResourceDesc("Thêm mới cấu hình hệ thống");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("config/updateconfig");
                        role.setResourceDesc("Chỉnh sửa cấu hình hệ thống");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("config/deleteconfig");
                        role.setResourceDesc("Xóa bỏ cấu hình hệ thống");
                        roleRepository.save(role);
                    }

                    // Nhom nhan su
                    roleGroup = new RoleGroup();
                    roleGroup.setGroupName("Quản lý nhóm nhân sự");
                    roleGroup = roleGroupRepository.save(roleGroup);

                    if (roleGroup.getId() > 0) {
                        Roles role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("empgroup/getallempgroups");
                        role.setResourceDesc("Xem danh sách nhóm nhân sự");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("empgroup/createempgroup");
                        role.setResourceDesc("Thêm mới nhóm nhân sự");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("empgroup/updateempgroup");
                        role.setResourceDesc("Chỉnh sửa nhóm nhân sự");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("empgroup/deleteempgroup");
                        role.setResourceDesc("Xóa bỏ nhóm nhân sự");
                        roleRepository.save(role);
                    }

                    // Nhan su
                    roleGroup = new RoleGroup();
                    roleGroup.setGroupName("Quản lý nhân sự");
                    roleGroup = roleGroupRepository.save(roleGroup);

                    if (roleGroup.getId() > 0) {
                        Roles role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("employee/getallemployees");
                        role.setResourceDesc("Xem danh sách nhân sự");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("employee/createemployee");
                        role.setResourceDesc("Thêm mới nhân sự");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("employee/updateemployee");
                        role.setResourceDesc("Chỉnh sửa nhân sự");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("employee/deleteemployee");
                        role.setResourceDesc("Xóa bỏ nhân sự");
                        roleRepository.save(role);
                    }

                    // Nhom quyen
                    roleGroup = new RoleGroup();
                    roleGroup.setGroupName("Quản lý nhóm quyền");
                    roleGroup = roleGroupRepository.save(roleGroup);

                    if (roleGroup.getId() > 0) {
                        Roles role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("rolegroup/getallrolegroups");
                        role.setResourceDesc("Xem danh sách nhóm quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("rolegroup/createrolegroup");
                        role.setResourceDesc("Thêm mới nhóm quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("rolegroup/updaterolegroup");
                        role.setResourceDesc("Chỉnh sửa nhóm quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("rolegroup/deleterolegroup");
                        role.setResourceDesc("Xóa bỏ nhóm quyền");
                        roleRepository.save(role);
                    }

                    // Quyen
                    roleGroup = new RoleGroup();
                    roleGroup.setGroupName("Quản lý quyền");
                    roleGroup = roleGroupRepository.save(roleGroup);

                    if (roleGroup.getId() > 0) {
                        Roles role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("roles/getallroles");
                        role.setResourceDesc("Xem danh sách quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("roles/getroles");
                        role.setResourceDesc("Xem chi tiết quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("roles/createroles");
                        role.setResourceDesc("Thêm mới quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("roles/updateroles");
                        role.setResourceDesc("Chỉnh sửa quyền");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("roles/deleteroles");
                        role.setResourceDesc("Xóa bỏ quyền");
                        roleRepository.save(role);
                    }

                    // Lich su - log
                    roleGroup = new RoleGroup();
                    roleGroup.setGroupName("Quản lý xem lịch sử hệ thống");
                    roleGroup = roleGroupRepository.save(roleGroup);
                    if (roleGroup.getId() > 0) {
                        Roles role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("systemlog/getConfigChangeLogs");
                        role.setResourceDesc("Lịch sử thay đổi cấu hình hệ thống");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("systemlog/getAccessLogs");
                        role.setResourceDesc("Lịch sử truy cập chức năng hệ thống");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("systemlog/getLoginHistory");
                        role.setResourceDesc("Lịch sử đăng nhập hệ thống");
                        roleRepository.save(role);

                        role = new Roles();
                        role.setRoleGroup(roleGroup);
                        role.setResourceUrl("systemlog/getUploadFileHistory");
                        role.setResourceDesc("Lịch sử upload file lên hệ thống");
                        roleRepository.save(role);
                    }

                    if (empGroup != null) {
                        List<Roles> listRoles = roleRepository.findAll();
                        Set<Roles> setRoles = new HashSet<>(listRoles);
                        empGroup.setRoles(setRoles);
                        empGroupRepository.save(empGroup);
                    }
                }

                logger.info("[END] DATABASE SERVICE CREATE SAMPLE DATA");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("CO LOI XAY RA KHI INIT SAMPLE DATA");
        }
    }
}
