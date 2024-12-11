import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
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
