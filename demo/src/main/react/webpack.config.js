var path = require('path');

module.exports = {
		entry:{
			records: "./src/ShowRecords.jsx"
		},
		devtool:'source-map',
		output: {
			path: path.join(__dirname, './../webapp/js/'),
			filename:"[name].min.js"
		},
		mode: 'production',
		module:{
			rules:[{
				test: /\.(js|jsx)$/,
				exclude: /node_modules/,
				loader:'babel-loader'
			}]
		},
		resolve:{
			modules:[path.resolve('./node_modules'), path.resolve('./src')],
			extensions:['.json', '.js']
		}

}