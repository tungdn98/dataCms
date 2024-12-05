import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SaleContract from './sale-contract';
import SaleContractDetail from './sale-contract-detail';
import SaleContractUpdate from './sale-contract-update';
import SaleContractDeleteDialog from './sale-contract-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SaleContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SaleContractUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SaleContractDetail} />
      <ErrorBoundaryRoute path={match.url} component={SaleContract} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SaleContractDeleteDialog} />
  </>
);

export default Routes;
