import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Button } from 'primereact/button';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const SearchComponent = ({
  initialValues = {},
  onSubmit, // Callback function for search results
  fields = [], // Array of field configurations (explained below)
}) => {
  const [searchValues, setSearchValues] = useState(initialValues);
  const [isLoading, setIsLoading] = useState(false);

  const handleInputChange = event => {
    setSearchValues({
      ...searchValues,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = () => {
    setIsLoading(true);

    // Build search criteria based on fields configuration
    const searchCriterials = fields.reduce((acc, field) => {
      if (searchValues[field.name] && searchValues[field.name].trim()) {
        acc[`${field.searchKey}.${field.searchType}`] = searchValues[field.name];
      }
      return acc;
    }, {});

    try {
      onSubmit(searchCriterials); // Pass results to parent component
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="d-flex justify-content-end align-items-center" style={{ height: '80%' }}>
      <ValidatedForm onSubmit={handleSubmit} className="d-flex align-items-center">
        {fields.map(field => (
          <ValidatedField
            key={field.name} // Ensure unique keys for accessibility
            label={field.label}
            id={field.name}
            name={field.name}
            type={field.type || 'text'} // Default to text input
            className={field.className}
            value={searchValues[field.name] || ''} // Set initial value
            placeholder={field.placeholder}
            onChange={handleInputChange}
            style={{ height: '38px', fontSize: '1rem', padding: '0.25rem 0.5rem', marginBottom: '1rem' }}
          />
        ))}
        <Button
          style={{ fontSize: '0.85rem', padding: '0.25rem 0.5rem', height: '36px', marginBottom: '6px' }}
          className="me-2 btn-sm"
          type="submit"
          color="info"
          disabled={isLoading}
        >
          <i className="pi pi-search" style={{ fontSize: '1rem' }}></i>
          <span className="ms-1">Search</span>
        </Button>
      </ValidatedForm>
    </div>
  );
};

export default SearchComponent;
