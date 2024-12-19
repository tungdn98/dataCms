import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Config from './config';
import ConfigLog from './config-log';
import EmpGroup from './emp-group';
import Employee from './employee';
import LoginHistory from './login-history';
import RoleGroup from './role-group';
import Roles from './roles';
import Company from './company';
import SaleOrder from './sale-order';
import SaleOpportunity from './sale-opportunity';
import SaleContract from './sale-contract';
import Product from './product';
import PaymentTerm from './payment-term';
import OpportunityStage from './opportunity-stage';
import OpportunityStageReason from './opportunity-stage-reason';
import EmployeeLead from './employee-lead';
import Customer from './customer';
import Currency from './currency';
import ContractType from './contract-type';
import Activity from './activity';
import ActivityType from './activity-type';
import ActivityObject from './activity-object';
import PaymentStatus from './payment-status';
import PaymentMethod from './payment-method';
import Unit from './unit';
import Finalcial from './finalcial';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}config`} component={Config} />
        <ErrorBoundaryRoute path={`${match.url}config-log`} component={ConfigLog} />
        <ErrorBoundaryRoute path={`${match.url}emp-group`} component={EmpGroup} />
        <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
        <ErrorBoundaryRoute path={`${match.url}login-history`} component={LoginHistory} />
        <ErrorBoundaryRoute path={`${match.url}role-group`} component={RoleGroup} />
        <ErrorBoundaryRoute path={`${match.url}roles`} component={Roles} />
        <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
        <ErrorBoundaryRoute path={`${match.url}sale-order`} component={SaleOrder} />
        <ErrorBoundaryRoute path={`${match.url}sale-opportunity`} component={SaleOpportunity} />
        <ErrorBoundaryRoute path={`${match.url}sale-contract`} component={SaleContract} />
        <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
        <ErrorBoundaryRoute path={`${match.url}payment-term`} component={PaymentTerm} />
        <ErrorBoundaryRoute path={`${match.url}opportunity-stage`} component={OpportunityStage} />
        <ErrorBoundaryRoute path={`${match.url}opportunity-stage-reason`} component={OpportunityStageReason} />
        <ErrorBoundaryRoute path={`${match.url}employee-lead`} component={EmployeeLead} />
        <ErrorBoundaryRoute path={`${match.url}customer`} component={Customer} />
        <ErrorBoundaryRoute path={`${match.url}currency`} component={Currency} />
        <ErrorBoundaryRoute path={`${match.url}contract-type`} component={ContractType} />
        <ErrorBoundaryRoute path={`${match.url}activity`} component={Activity} />
        <ErrorBoundaryRoute path={`${match.url}activity-type`} component={ActivityType} />
        <ErrorBoundaryRoute path={`${match.url}activity-object`} component={ActivityObject} />
        <ErrorBoundaryRoute path={`${match.url}payment-status`} component={PaymentStatus} />
        <ErrorBoundaryRoute path={`${match.url}payment-method`} component={PaymentMethod} />
        <ErrorBoundaryRoute path={`${match.url}unit`} component={Unit} />
        <ErrorBoundaryRoute path={`${match.url}finalcial`} component={Finalcial} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
