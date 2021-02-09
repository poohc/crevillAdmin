const path = require('path');

module.exports = {
	mode: "development",
	entry: {
		index: "./index.js"
	},
	output: {
		filename: '[name]_bundle.js',
		path: path.resolve(__dirname, 'public'),
	},
	resolve: {
		alias: {
			vue: 'vue/dist/vue.js'
		}
	},
	module: {
		rules: [
			{
				test: /\.css$/,
				use: [
					`style-loader`,
					`css-loader`,
				]
			},
			{
				test: /\.(ico|png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
				use: [
					{
						loader: 'file-loader',
						options: {
							name: '[name].[ext]'
						}
					}
				]
			}
		],
	}
};