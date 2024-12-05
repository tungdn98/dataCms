import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeLead from './employee-lead';
import EmployeeLeadDetail from './employee-lead-detail';
import EmployeeLeadUpdate from './employee-lead-update';
import EmployeeLeadDeleteDialog from './employee-lead-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeLeadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeLeadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeLeadDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeLead} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeLeadDeleteDialog} />
  </>
);

export default Routes;
