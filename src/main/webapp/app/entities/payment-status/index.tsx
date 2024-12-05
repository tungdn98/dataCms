import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaymentStatus from './payment-status';
import PaymentStatusDetail from './payment-status-detail';
import PaymentStatusUpdate from './payment-status-update';
import PaymentStatusDeleteDialog from './payment-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaymentStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaymentStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaymentStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaymentStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaymentStatusDeleteDialog} />
  </>
);

export default Routes;
