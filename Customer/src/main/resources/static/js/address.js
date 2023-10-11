// address.js
// address.js
document.addEventListener('DOMContentLoaded', function() {
    var provinceSelect = document.getElementById('province');
    var districtSelect = document.getElementById('district');
    var wardSelect = document.getElementById('ward');

    fetch('https://provinces.open-api.vn/api/?depth=3')
        .then(response => response.json())
        .then(data => {
            var daNang = data.find(province => province.codename === 'thanh_pho_da_nang');
            if (daNang) {
                var option = document.createElement('option');
                option.text = daNang.name;
                option.value = daNang.name; // Sử dụng tên thay vì mã số
                provinceSelect.add(option);

                daNang.districts.forEach(district => {
                    var option = document.createElement('option');
                    option.text = district.name;
                    option.value = district.name; // Sử dụng tên thay vì mã số
                    districtSelect.add(option);
                });
            }
        });

    districtSelect.addEventListener('change', function() {
        fetch(`https://provinces.open-api.vn/api/?depth=3`)
            .then(response => response.json())
            .then(data => {
                wardSelect.options.length = 0;
                var daNang = data.find(province => province.codename === 'thanh_pho_da_nang');
                if (daNang) {
                    var wards = daNang.districts.find(district => district.name == this.value).wards;
                    wards.forEach(ward => {
                        var option = document.createElement('option');
                        option.text = ward.name;
                        option.value = ward.name; // Sử dụng tên thay vì mã số
                        wardSelect.add(option);
                    });
                }
            });
    });
});

/*
* // address.js
document.addEventListener('DOMContentLoaded', function() {
    var provinceSelect = document.getElementById('province');
    var districtSelect = document.getElementById('district');
    var wardSelect = document.getElementById('ward');

    fetch('https://provinces.open-api.vn/api/?depth=3')
        .then(response => response.json())
        .then(data => {
            data.forEach(province => {
                var option = document.createElement('option');
                option.text = province.name;
                option.value = province.name; // Sử dụng tên thay vì mã số
                provinceSelect.add(option);
            });
        });

    provinceSelect.addEventListener('change', function() {
        fetch(`https://provinces.open-api.vn/api/?depth=3`)
            .then(response => response.json())
            .then(data => {
                districtSelect.options.length = 0;
                var districts = data.find(province => province.name == this.value).districts;
                districts.forEach(district => {
                    var option = document.createElement('option');
                    option.text = district.name;
                    option.value = district.name; // Sử dụng tên thay vì mã số
                    districtSelect.add(option);
                });
            });
    });

    districtSelect.addEventListener('change', function() {
        fetch(`https://provinces.open-api.vn/api/?depth=3`)
            .then(response => response.json())
            .then(data => {
                wardSelect.options.length = 0;
                var wards = data.find(province => province.name == provinceSelect.value).districts.find(district => district.name == this.value).wards;
                wards.forEach(ward => {
                    var option = document.createElement('option');
                    option.text = ward.name;
                    option.value = ward.name; // Sử dụng tên thay vì mã số
                    wardSelect.add(option);
                });
            });
    });
});
*
*
* */