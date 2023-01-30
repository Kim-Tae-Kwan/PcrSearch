$(() => {
	
	$('#sidoSelect').on('change', async (e) => {
		let sidoName = $(e.target).val();
		let gguNames =  await getGguNames(sidoName);
		appendGguName(gguNames);
	});
	
	$('#gguSelect').on('change', async (e) => {
		let sidoName = $('#sidoSelect').val();
		let gguName = $(e.target).val();
		
		let hospitals = await getHospitals(sidoName, gguName);
		appendHospital(hospitals);
		
	});
	
});

let getGguNames = async (sidoName) => {
	let res = await fetch(`http://localhost:8080/api/hospital/${sidoName}/gguName`);
	return await res.json();
};

let getHospitals = async (sidoName, gguName) => {
	let res = await fetch(`http://localhost:8080/api/hospital/${sidoName}/${gguName}`);
	return await res.json();
};

let appendGguName = (gguNames) => {
	$('#gguSelect').empty();
	gguNames.map((gguName, index) => {
		let option = `<option value=${gguName}>${gguName}</option>`;
		$('#gguSelect').append(option);
	});
	
};

let appendHospital = (hospitals) => {
	$('tbody').empty();
	hospitals.map((hospital, index) => {
		let tr = `<tr>
						<td>${index + 1}</td>
						<td>${hospital.address}</td>
						<td>${hospital.name}</td>
						<td>${hospital.tel}</td>
						<td>${hospital.inspectionYn}</td>
						<td>${hospital.prescriptionYn}</td>
					</tr>`;
		$('tbody').append(tr);
	});
};