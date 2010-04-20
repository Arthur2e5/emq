/**
 * dwr�Զ������ͼƬ
 */
function useLoadingImage(imageSrc) {
  var loadingImage;
  if (imageSrc) loadingImage = imageSrc;
  // ���ͼƬû�Ž���
  else loadingImage = "ajax-loader.gif";
  DWREngine.setPreHook(function() {
    var disabledImageZone = $('disabledImageZone');
    if (!disabledImageZone) {
      disabledImageZone = document.createElement('div');
      disabledImageZone.setAttribute('id', 'disabledImageZone');
      disabledImageZone.style.position = "absolute";
      disabledImageZone.style.zIndex = "1000";
      disabledImageZone.style.left = "0px";
      disabledImageZone.style.top = "0px";
      disabledImageZone.style.width = "100%";
      disabledImageZone.style.height = "100%";
      var imageZone = document.createElement('img');
      imageZone.setAttribute('id','imageZone');
      imageZone.setAttribute('src',imageSrc);
      imageZone.style.position = "absolute";
      imageZone.style.top = "0px";
      imageZone.style.right = "0px";
      disabledImageZone.appendChild(imageZone);
      document.body.appendChild(disabledImageZone);
    }
    else {
      $('imageZone').src = imageSrc;
      disabledImageZone.style.visibility = 'visible';
    }
  });
  DWREngine.setPostHook(function() {
    $('disabledImageZone').style.visibility = 'hidden';
  });
}	

/**
 * dwr�Զ��������Ϣ
 */
function useLoadingMessage(message) {
  var loadingMessage;
  if (message) loadingMessage = message;
  else loadingMessage = "���ڼ������ݣ����Ժ�...";

  DWREngine.setPreHook(function() {
    var disabledZone = $('disabledZone');
    if (!disabledZone) {
      disabledZone = document.createElement('div');
      disabledZone.setAttribute('id', 'disabledZone');
      disabledZone.style.position = "absolute";
      disabledZone.style.zIndex = "1000";
      disabledZone.style.left = "0px";
      disabledZone.style.top = "0px";
      disabledZone.style.width = "100%";
      disabledZone.style.height = "100%";
      document.body.appendChild(disabledZone);
      var messageZone = document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
      messageZone.style.position = "absolute";
      messageZone.style.top = "0px";����//������ʾ������Ϣ���λ��
      messageZone.style.right = "0px";�� //������ʾ������Ϣ���λ��
      messageZone.style.width = "150";����//������ʾ������Ϣ��Ŀ��
      messageZone.style.height = "20";    //������ʾ������Ϣ��ĸ߶�
      messageZone.style.background = "red";//������ʾ������Ϣ�����ɫ
      messageZone.style.color = "white";
      messageZone.style.fontFamily = "����";
      messageZone.style.fontSize="9pt";
      messageZone.style.padding = "4px";
      disabledZone.appendChild(messageZone);
      var text = document.createTextNode(loadingMessage);
      messageZone.appendChild(text);
    }
    else {
      $('messageZone').innerHTML = loadingMessage;
      disabledZone.style.visibility = 'visible';
    }
  });

  DWREngine.setPostHook(function() {
    $('disabledZone').style.visibility = 'hidden';
  });
}