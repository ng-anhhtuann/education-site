import React, { useState } from 'react';
import "./search.css"
import SearchIcon from '@mui/icons-material/Search';

const SearchBar = () => {
  const [showDropdown, setShowDropdown] = useState(false);
  const [dropdownData, setDropdownData] = useState([
    { label: 'Option 1', checked: false, inputValue: '' },
    { label: 'Option 2', checked: false, inputValue: '' },
    { label: 'Option 3', checked: false, inputValue: '' },
  ]);

  const handleDropdownToggle = () => {
    setShowDropdown(!showDropdown);
  };

  const handleCheckboxChange = (index) => {
    const updatedData = [...dropdownData];
    updatedData[index].checked = !updatedData[index].checked;
    setDropdownData(updatedData);
  };

  const handleInputChange = (e, index) => {
    const updatedData = [...dropdownData];
    updatedData[index].inputValue = e.target.value;
    setDropdownData(updatedData);
  };

  const handleSearch = () => {
    const filteredData = dropdownData
      .filter((item) => item.checked)
      .map(({ label, inputValue }) => ({ label, inputValue }));

    const searchObject = {
      fields: filteredData,
      searchType: 'ALL',
    };

    console.log(JSON.stringify(searchObject));
  };

  return (
    <div className="SearchBar">
      <button className="DropdownButton" onClick={handleDropdownToggle}>ALL</button>
      <button className="SearchButton" onClick={handleSearch}>
        <SearchIcon />
      </button>
      {showDropdown && (
        <div>
          {dropdownData.map((item, index) => (
            <div className="CheckboxContainer" key={index}>
              <input
                className="Checkbox"
                type="checkbox"
                checked={item.checked}
                onChange={() => handleCheckboxChange(index)}
              />
              <label>{item.label}</label>
              {item.checked && (
                <input
                  className="TextInput"
                  type="text"
                  value={item.inputValue}
                  onChange={(e) => handleInputChange(e, index)}
                />
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchBar;
