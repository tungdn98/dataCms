import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const ConfigMenuList = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="wrench" to="/config">
        Config
      </MenuItem>
      <MenuItem icon="file-pen" to="/config-log">
        Config Log
      </MenuItem>
      <MenuItem icon="users-cog" to="/emp-group">
        Emp Group
      </MenuItem>
      <MenuItem icon="circle-user" to="/employee">
        Employee
      </MenuItem>
      <MenuItem icon="clock-rotate-left" to="/login-history">
        Login History
      </MenuItem>
      <MenuItem icon="layer-group" to="/role-group">
        Role Group
      </MenuItem>
      <MenuItem icon="hat-cowboy" to="/roles">
        Roles
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default ConfigMenuList as React.ComponentType<any>;
