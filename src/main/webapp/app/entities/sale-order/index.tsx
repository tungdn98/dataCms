import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SaleOrder from './sale-order';
import SaleOrderDetail from './sale-order-detail';
import SaleOrderUpdate from './sale-order-update';
import SaleOrderDeleteDialog from './sale-order-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SaleOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SaleOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SaleOrderDetail} />
      <ErrorBoundaryRoute path={match.url} component={SaleOrder} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SaleOrderDeleteDialog} />
  </>
);

export default Routes;
