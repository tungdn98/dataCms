export interface IEmployeeLead {
  id?: number;
  leadId?: string | null;
  employeeId?: string | null;
  leadCode?: string;
  leadName?: string | null;
  leadPotentialLevelId?: string | null;
  leadSourceId?: string | null;
  leadPotentialLevelName?: string | null;
  leadSourceName?: string | null;
}

export const defaultValue: Readonly<IEmployeeLead> = {};
