import flask
from flask import Flask, request, render_template, jsonify, redirect, abort, Response
import imageResize
import classificationModel
from prometheus_flask_exporter import PrometheusMetrics

app = Flask(__name__)
metrics = PrometheusMetrics(app)
@app.route('/')
def main():
    return 'OK'

@app.route("/upload")
def home():
    return render_template('checkUpload.html')

def allowed_file(filename):
    # print(filename)
    ALLOWED_EXTENSIONS = set(['jpg', 'png', 'jpeg'])

    file = filename.split('.')
    if len(file) == 2:
        if file[1] in ALLOWED_EXTENSIONS:
            return True
        else:
            return False
    else:
        return False

@app.route("/image", methods = ['POST'])
def imageClassification():
    if 'file' not in request.files:
        return redirect(request.url)

    image = request.files['file']

    if image.filename == '':
        abort(404)
        return redirect(request.url)

    if allowed_file(image.filename):
        inputImage = imageResize.imageResize(image)

        model = classificationModel.loadModel()

        result = classificationModel.classification(model, inputImage)

        return jsonify(result)
    else:
        abort(404)
        return redirect(request.url)

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)


