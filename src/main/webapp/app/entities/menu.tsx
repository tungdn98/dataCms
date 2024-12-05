import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/config">
        Config
      </MenuItem>
      <MenuItem icon="asterisk" to="/config-log">
        Config Log
      </MenuItem>
      <MenuItem icon="asterisk" to="/emp-group">
        Emp Group
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        Employee
      </MenuItem>
      <MenuItem icon="asterisk" to="/login-history">
        Login History
      </MenuItem>
      <MenuItem icon="asterisk" to="/role-group">
        Role Group
      </MenuItem>
      <MenuItem icon="asterisk" to="/roles">
        Roles
      </MenuItem>
      <MenuItem icon="asterisk" to="/company">
        Company
      </MenuItem>
      <MenuItem icon="asterisk" to="/sale-order">
        Sale Order
      </MenuItem>
      <MenuItem icon="asterisk" to="/sale-opportunity">
        Sale Opportunity
      </MenuItem>
      <MenuItem icon="asterisk" to="/sale-contract">
        Sale Contract
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/payment-term">
        Payment Term
      </MenuItem>
      <MenuItem icon="asterisk" to="/opportunity-stage">
        Opportunity Stage
      </MenuItem>
      <MenuItem icon="asterisk" to="/opportunity-stage-reason">
        Opportunity Stage Reason
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee-lead">
        Employee Lead
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        Customer
      </MenuItem>
      <MenuItem icon="asterisk" to="/currency">
        Currency
      </MenuItem>
      <MenuItem icon="asterisk" to="/contract-type">
        Contract Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity">
        Activity
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity-type">
        Activity Type
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity-object">
        Activity Object
      </MenuItem>
      <MenuItem icon="asterisk" to="/payment-status">
        Payment Status
      </MenuItem>
      <MenuItem icon="asterisk" to="/payment-method">
        Payment Method
      </MenuItem>
      <MenuItem icon="asterisk" to="/unit">
        Unit
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
