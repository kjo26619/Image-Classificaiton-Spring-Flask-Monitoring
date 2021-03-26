from torchvision import transforms
from PIL import Image

def imageResize(image):

    img = Image.open(image)

    # img.show()

    preprocess = transforms.Compose([
        transforms.Resize(256),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        transforms.Normalize(mean = [0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])

    input_tensor = preprocess(img)

    return input_tensor

