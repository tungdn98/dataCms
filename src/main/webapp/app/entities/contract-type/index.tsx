import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContractType from './contract-type';
import ContractTypeDetail from './contract-type-detail';
import ContractTypeUpdate from './contract-type-update';
import ContractTypeDeleteDialog from './contract-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContractTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContractTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContractTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContractType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContractTypeDeleteDialog} />
  </>
);

export default Routes;
