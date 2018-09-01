
import React from 'react'
import ReactDOM from 'react-dom'
import $ from "jquery";

class Records extends React.Component{

	constructor(props){
		super(props);
		this.state = {
			recordsList: null
		};
	}
	
	componentDidMount(){
		$.ajax({
            type : 'GET',
            contentType : "application/json",
            headers : null,
            url : "/getRecords",
            data : '',
            dataType : "json",
            success : function(response) {
                console.log(response);
                this.setState({
                	recordsList: response
                });
            }.bind(this),
            error : function(xhr, status, err) {
               
            }.bind(this),
            async : true
        });
	}
	
	render(){
		return(
			<div>
			{
				this.state.recordsList && Object.keys(this.state.recordsList).map((key) => {
  					return <PopulateData key={key} recordsData={this.state.recordsList[key]}  header={key} />
  				})
  				
  			}
			</div>
		);
	}
}

const PopulateData = ({recordsData, header}) => {
	return (
		<div>
			<h1>{header}</h1>
			<div>
			{
				recordsData.map(record => 
					<SingleRecord {...record}
						key={record.id}
					/>
				)
				
			}
			</div>
		</div>
	);
}

const SingleRecord = ({firstName, lastName, company, email, address1, address2, city, state, stateAbbr, zip, phone}) =>{
	return (
		<div>
			<span>{firstName}</span>
			<span>  |  </span>
			<span>{lastName}</span>
			<span>  |  </span>
			<span>{company}</span>
			<span>  |  </span>
			<span>{email}</span>
			<span>  |  </span>
			<span>{address1}</span>
			<span>  |  </span>
			<span>{address2}</span>
			<span>  |  </span>
			<span>{city}</span>
			<span>  |  </span>
			<span>{state}</span>
			<span>  |  </span>
			<span>{stateAbbr}</span>
			<span>  |  </span>
			<span>{zip}</span>
			<span>  |  </span>
			<span>{phone}</span>
		</div>
	);
}


ReactDOM.render(
  <Records/>,
  document.getElementById('reactContainer')
);