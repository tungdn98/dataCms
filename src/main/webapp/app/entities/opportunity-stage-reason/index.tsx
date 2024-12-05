import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OpportunityStageReason from './opportunity-stage-reason';
import OpportunityStageReasonDetail from './opportunity-stage-reason-detail';
import OpportunityStageReasonUpdate from './opportunity-stage-reason-update';
import OpportunityStageReasonDeleteDialog from './opportunity-stage-reason-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OpportunityStageReasonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OpportunityStageReasonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OpportunityStageReasonDetail} />
      <ErrorBoundaryRoute path={match.url} component={OpportunityStageReason} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OpportunityStageReasonDeleteDialog} />
  </>
);

export default Routes;
